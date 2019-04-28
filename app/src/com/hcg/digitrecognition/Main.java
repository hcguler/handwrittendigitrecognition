package com.hcg.digitrecognition;


import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnsupportedKerasConfigurationException, IOException, InvalidKerasConfigurationException {
        final File file = new File("../handwrittendigitrecognition/trainer/mnist.h5f");
        final File file_dlturkiye = new File("../handwrittendigitrecognition/trainer/mnist_dlturkiye.h5f");
        if (!file.exists()) {
            System.out.println("The Keras model file does not exits. Run the mnist-trainer.py"
                + " python script first");
        }
 if (!file_dlturkiye.exists()) {
            System.out.println("The Keras model file does not exits. Run the mnist-trainer.py"
                + " python script first");
        }

        MultiLayerNetwork mnistModel = KerasModelImport.importKerasSequentialModelAndWeights(file.getAbsolutePath(),true);
        MultiLayerNetwork mnistModel_dlTurkiye = KerasModelImport.importKerasSequentialModelAndWeights(file_dlturkiye.getAbsolutePath(),true);
        System.out.println("Hello World!");
    }
}
