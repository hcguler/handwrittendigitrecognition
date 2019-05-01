package com.hcg.digitrecognition.ui;

import com.hcg.digitrecognition.DigitDetector;
import com.hcg.digitrecognition.Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;

/**
 * Created by Mika on 27/03/17.
 */
public class MainPanel extends JPanel {

  private static final int TEXTBOX_HEIGHT = 50;

  private final JPanel drawPanelContainer;
  private final DrawPanel drawPanel;
  private final JTextArea resultText;
  private JButton calculateButton;
  private JButton clearButton;
  private final DigitDetector digitDetector;

  public MainPanel() throws UnsupportedKerasConfigurationException,
      IOException,
      InvalidKerasConfigurationException {
    // Init fields
    digitDetector = new DigitDetector();
    drawPanel = new DrawPanel();
    drawPanelContainer = new JPanel();
    resultText = new JTextArea();
    initializeClearButton();
    initializeCalculateButton();
    drawPanelContainer.setBackground(Color.LIGHT_GRAY);
    // Layout panels
    final JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setComponentSizes();
    final JPanel buttonContainer = new JPanel();
    buttonContainer.setLayout(new GridBagLayout());
    buttonContainer.add(calculateButton);
    buttonContainer.add(clearButton);
    drawPanelContainer.add(drawPanel);
    drawPanelContainer.add(buttonContainer);
    drawPanelContainer.add(resultText);
    topContainer.add(drawPanelContainer);
    this.add(topContainer);
  }

  private void initializeCalculateButton() {
    calculateButton = new JButton();
    calculateButton.setText("Calculate");
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
        resultText.setText(digitDetector.recognize(mnistInputImage));
      }
    });
  }

  private void initializeClearButton() {
    clearButton = new JButton();
    clearButton.setText("Clear");
    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        drawPanel.clear();
      }
    });
  }

  private void setComponentSizes() {
    final int half = Main.UI_SIZE / 2;
    drawPanelContainer.setPreferredSize(new Dimension(half, Main.UI_SIZE));
    drawPanel.setPreferredSize(new Dimension(half, half));
    resultText.setPreferredSize(new Dimension(half, TEXTBOX_HEIGHT));
    clearButton.setPreferredSize(new Dimension(half/2, TEXTBOX_HEIGHT));
    calculateButton.setPreferredSize(new Dimension(half/2, TEXTBOX_HEIGHT));
  }


}
