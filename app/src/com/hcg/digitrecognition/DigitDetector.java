package com.hcg.digitrecognition;

import com.hcg.digitrecognition.ui.ImageUtils;
import java.awt.*;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DigitDetector {

  private final MultiLayerNetwork mnistModel;
  private final MultiLayerNetwork mnistModel_dlturkiye;

  public DigitDetector() throws IOException,
      InvalidKerasConfigurationException,
      UnsupportedKerasConfigurationException {

    final File file = new File("../handwrittendigitrecognition/trainer/mnist.h5f");
    final File file_dlturkiye = new File("../handwrittendigitrecognition/trainer/mnist_dlturkiye.h5f");

    if (!file.exists()) {
      throw new IOException("The Keras model file does not exits. Run the mnist-trainer.py"
          + " python script first");
    }
    if (!file_dlturkiye.exists()) {
      throw new IOException("The Keras model file does not exits. Run the mnist-trainer.py"
          + " python script first");
    }

    mnistModel = KerasModelImport.importKerasSequentialModelAndWeights(file.getAbsolutePath());
    mnistModel_dlturkiye = KerasModelImport.importKerasSequentialModelAndWeights(file_dlturkiye.getAbsolutePath());
  }

  public String recognize(final BufferedImage image) {
    final double[] pixels = ImageUtils.getPixelsAndConvertToBlackAndWhite(image);
    final int[] inputDataShape = new int[]{1,
        1,
        ImageUtils.MNIST_IMAGE_SIZE,
        ImageUtils.MNIST_IMAGE_SIZE};
    // 'K' means memory layout order as defined in
    // https://docs.scipy.org/doc/numpy/reference/generated/numpy.array.html#numpy.array
    final NDArray drawnDigit = new NDArray(pixels, inputDataShape, 'a');
    final INDArray output = mnistModel.output(drawnDigit);
    final INDArray output_dlturkiye = mnistModel_dlturkiye.output(drawnDigit);

    StringBuilder stringBuilder = new StringBuilder("First recognize: ")
        .append(findLargestIndex(output))
        .append("\n")
        .append("DL Türkiye recognize: ")
        .append(findLargestIndex(output_dlturkiye));
    return stringBuilder.toString();
  }

  private int findLargestIndex(INDArray array) {
    int largestIndex = 0;
    double largestValue = Double.MIN_VALUE;

    for (int i = 0; i < array.lengthLong(); i++) {
      if (array.getDouble(i) > largestValue) {
        largestIndex = i;
        largestValue = array.getDouble(i);
      }
    }
    return largestIndex;
  }
}
