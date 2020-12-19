package c0d1red.infrastructure.utils;

import c0d1red.domain.TrainObject;

import java.util.List;

public class ConvertUtil {
    private static final int OUTPUT_COLUMNS = 1;
    private static final int RESULT_ROWS = 1;

    public static double[][] convertTrainInputsToMatrix(List<TrainObject> trainObjects) {
        int rows = trainObjects.size();
        int columns = trainObjects.get(0).getInputs().size();
        double[][] array = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                array[i][j] = trainObjects.get(i).getInputs().get(j);
            }
        }
        return array;
    }

    public static double[][] convertTrainOutputsToMatrix(List<TrainObject> trainObjects) {
        int rows = trainObjects.size();
        double[][] array = new double[rows][OUTPUT_COLUMNS];
        for (int row = 0; row < rows; row++) {
            array[row][0] = trainObjects.get(row).getOutput();
        }
        return array;
    }

    public static double[][] convertResultToMatrix(List<Double> result) {
        int columns = result.size();
        double[][] array = new double[RESULT_ROWS][columns];
        for (int column = 0; column < columns; column++) {
            array[0][column] = result.get(column);
        }
        return array;
    }
}
