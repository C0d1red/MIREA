package c0d1red.infrastructure;

import c0d1red.domain.DataTable;
import c0d1red.domain.FootballStatMap;
import c0d1red.domain.TrainObject;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

import static c0d1red.infrastructure.DataHeaderConstants.*;
import static c0d1red.infrastructure.NeuralNetworkConfiguration.getFootballPredictionConfiguration;
import static c0d1red.infrastructure.utils.ConvertUtil.*;

public class FootballPredictionSystem {
    private static final int WIN = 1;
    private static final int DRAW_OR_LOSE = 0;
    private static final double FRACTION_TRAIN = 0.8;
    private static final int EPOCH_COUNT = 1000;
    private static MultiLayerNetwork network;

    public FootballPredictionSystem(int inputsNum) {
        network = new MultiLayerNetwork(getFootballPredictionConfiguration(inputsNum));
    }

    public double predict(FootballStatMap stats, String team, String rivalTeam) {
        List<Double> teamStat = stats.getStatFor(team);
        List<Double> rivalTeamStat = stats.getStatFor(rivalTeam);
        List<Double> resultStat = calculateResultVector(teamStat, rivalTeamStat);
        INDArray features = Nd4j.create(convertResultToMatrix(resultStat));
        return network.output(features).getDouble();
    }

    public Evaluation train(List<TrainObject> trainObjects) {
        INDArray features = Nd4j.create(convertTrainInputsToMatrix(trainObjects));
        INDArray labels = Nd4j.create(convertTrainOutputsToMatrix(trainObjects));
        DataSet dataSet = new DataSet(features, labels);

        SplitTestAndTrain splitTestAndTrain = dataSet.splitTestAndTrain(FRACTION_TRAIN);
        DataSet train = splitTestAndTrain.getTrain();
        DataSet test = splitTestAndTrain.getTest();
        normalizeTestAndTrainData(train, test);

        trainNetwork(train);
        return evaluateTestResults(test);
    }

    public List<TrainObject> createTestData(DataTable dataTable, FootballStatMap stats) {
        List<TrainObject> trainObjects = new ArrayList<>();
        for (int row = 0; row < dataTable.rowCount(); row++) {
            String teamName = dataTable.getValueBy(row, TEAM_NAME);
            String rivalTeamName = dataTable.getValueBy(row, RIVAL_TEAM_NAME);
            String winnerTeam = dataTable.getValueBy(row, WINNER_TEAM_NAME);
            TrainObject trainObject = createTrainObject(stats, teamName, rivalTeamName, winnerTeam);
            trainObjects.add(trainObject);
        }
        return trainObjects;
    }

    private void normalizeTestAndTrainData(DataSet train, DataSet test) {
        DataNormalization dataNormalization = new NormalizerStandardize();
        dataNormalization.fit(train);
        dataNormalization.transform(train);
        dataNormalization.fit(test);
    }

    private void trainNetwork(DataSet train) {
        for (int epoch = 0; epoch < EPOCH_COUNT; epoch++) {
            network.fit(train);
        }
    }

    private Evaluation evaluateTestResults(DataSet test) {
        Evaluation evaluation = new Evaluation(2);
        evaluation.eval(test.getLabels(), network.output(test.getFeatures()));
        return evaluation;
    }

    private TrainObject createTrainObject(FootballStatMap stats, String teamName, String rivalTeamName, String winnerTeam) {
        List<Double> homeTeamStat = stats.getStatFor(teamName);
        List<Double> awayTeamStat = stats.getStatFor(rivalTeamName);
        List<Double> resultStat = calculateResultVector(homeTeamStat, awayTeamStat);
        int matchResult = calculateMatchResult(teamName, winnerTeam);
        return new TrainObject(resultStat, matchResult);
    }

    private List<Double> calculateResultVector(List<Double> homeTeamStat, List<Double> awayTeamStat) {
        List<Double> resultVector = new ArrayList<>();
        for (int stat = 0; stat < homeTeamStat.size(); stat++) {
            double result = homeTeamStat.get(stat) - awayTeamStat.get(stat);
            resultVector.add(result);
        }
        return resultVector;
    }

    private int calculateMatchResult(String homeTeamName, String winnerTeam) {
        return winnerTeam.equals(homeTeamName) ? WIN : DRAW_OR_LOSE;
    }

}
