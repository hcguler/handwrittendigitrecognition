package com.hcg.digitrecognition;


import com.hcg.digitrecognition.constants.UiConstants;
import com.hcg.digitrecognition.ui.MainPanel;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;

public class Main {

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        createFrame().setVisible(true);
      } catch (InvalidKerasConfigurationException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (UnsupportedKerasConfigurationException e) {
        e.printStackTrace();
      }
    });
  }

  private static JFrame createFrame() throws InvalidKerasConfigurationException,
      IOException,
      UnsupportedKerasConfigurationException {
    final JFrame frame = new JFrame(UiConstants.MAIN_PANEL_HEADER);
    frame.setSize(UiConstants.MAIN_PANEL_WIDTH, UiConstants.MAIN_PANEL_HEIGHT);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final MainPanel mainUi = new MainPanel();
    frame.add(mainUi);
    return frame;
  }
}
