package c0d1red.infrastructure;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class NeuralNetworkConfiguration {

    public static MultiLayerConfiguration getFootballPredictionConfiguration(int inputSize) {
        return new NeuralNetConfiguration.Builder()
                .updater(new Sgd(0.1))
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(50)
                        .activation(Activation.IDENTITY)
                        .build()
                )
                .layer(new DenseLayer.Builder()
                        .nIn(50)
                        .nOut(20)
                        .activation(Activation.SOFTMAX)
                        .build()
                )
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nIn(20)
                        .nOut(1)
                        .activation(Activation.SIGMOID)
                        .build()
                )
                .build();
    }

}
