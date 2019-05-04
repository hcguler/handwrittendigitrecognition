package com.hcg.digitrecognition.ui;

import com.hcg.digitrecognition.DigitDetector;
import com.hcg.digitrecognition.constants.UiConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;

/**
 * Created by Mika on 27/03/17.
 */
public class MainPanel extends JPanel {


  private final JPanel drawPanelContainer;
  private final DrawPanel drawPanel;
  private JTextPane resultText;
  private JButton calculateButton;
  private JButton clearButton;
  private JTextPane infoText;
  private final DigitDetector digitDetector;

  public MainPanel() throws UnsupportedKerasConfigurationException,
      IOException,
      InvalidKerasConfigurationException {
    digitDetector = new DigitDetector();
    drawPanel = new DrawPanel();
    drawPanelContainer = new JPanel();

    initializeClearButton();
    initializeCalculateButton();
    initializeInfoText();
    initializeResultText();
    setComponentSizes();
    initializeDesignLayout();
  }

  private void initializeDesignLayout() {
    drawPanelContainer.setBackground(Color.LIGHT_GRAY);
    final JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    final JPanel buttonContainer = new JPanel();
    buttonContainer.setLayout(new GridBagLayout());
    buttonContainer.add(calculateButton);
    buttonContainer.add(clearButton);
    drawPanelContainer.add(infoText);
    drawPanelContainer.add(drawPanel);
    drawPanelContainer.add(buttonContainer);
    drawPanelContainer.add(resultText);
    topContainer.add(drawPanelContainer);
    this.add(topContainer);
  }

  private void initializeResultText() {
    resultText = new JTextPane();
    resultText.setContentType("text/html");
  }


  private void initializeInfoText() {
    infoText = new JTextPane();
    infoText.setContentType("text/html");
    infoText.setSize(new Dimension(UiConstants.DRAWABLE_PANEL_WIDTH, UiConstants.TEXTBOX_HEIGHT));
    infoText.setText("<html><center><font size=4 color=rgb(1,1,1)>  "
        + UiConstants.INFO_TEXT
        + "   </font></center></html>");
  }

  private void initializeCalculateButton() {
    calculateButton = new JButton();
    calculateButton.setText(UiConstants.CALCULATE_BUTTON_TEXT);
    calculateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        //panele çizilen resim alınır.
        BufferedImage image = drawPanel.createImage();
        //tam çizimi içine alan rectangle hesaplanır.
        final Rectangle bounds = ImageUtils.findBoundsOfBlackShape(image);
        final Dimension newDim = ImageUtils.getScaledMnistDigitDimensions(bounds);
        //panel içindeki resim hiçbir parçası dışarda kalmayacak şekilde kesilir.
        final BufferedImage cropped = image.getSubimage(bounds.x,
            bounds.y,
            bounds.width,
            bounds.height);

        // MNIST veri setine benzer bir şekilde tam kapsanan sayı bilgisi 20x20 piksel şekil verilir.
        final BufferedImage scaled = ImageUtils.scale(cropped, newDim.width, newDim.height);

        // 20x20 yapılan resim arka plan yerleştirilerek 28x28 olarak güncellenir.
        // sayı tam ortada olacak şekilde çerçeve eklenmiş olur.
        final BufferedImage mnistInputImage
            = ImageUtils.addImageToCenter(scaled,
            ImageUtils.MNIST_IMAGE_SIZE,
            ImageUtils.MNIST_IMAGE_SIZE);
        //kullanılabilir durumda olan h5f yapay sinir ağlarından sonuçlar oluşturulur.
        resultText.setText("<html><center><b><font size=5 color=rgb(255,0,0)>  "
            + digitDetector.recognize(mnistInputImage)
            + "   </font></b></center></html>");
      }
    });
  }

  private void initializeClearButton() {
    clearButton = new JButton();
    clearButton.setText(UiConstants.CLEAR_BUTTON_TEXT);
    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        drawPanel.clear();
        resultText.setText("");
      }
    });
  }

  private void setComponentSizes() {
    drawPanelContainer.setPreferredSize(new Dimension(UiConstants.DRAWABLE_PANEL_WIDTH, UiConstants.DRAWABLE_PANEL_HEIGHT));
    drawPanel.setPreferredSize(new Dimension(UiConstants.DRAWABLE_PANEL_WIDTH, UiConstants.DRAWABLE_PANEL_HEIGHT));
    resultText.setPreferredSize(new Dimension(UiConstants.TEXTBOX_WIDTH, UiConstants.TEXTBOX_HEIGHT));
    clearButton.setPreferredSize(new Dimension(UiConstants.BUTTON_WIDTH, UiConstants.BUTTON_HEIGHT));
    calculateButton.setPreferredSize(new Dimension(UiConstants.BUTTON_WIDTH, UiConstants.BUTTON_HEIGHT));
  }


}
