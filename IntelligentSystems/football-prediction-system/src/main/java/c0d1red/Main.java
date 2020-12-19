package c0d1red;

import c0d1red.domain.DataTable;
import c0d1red.domain.FootballStatMap;
import c0d1red.domain.TrainObject;
import c0d1red.infrastructure.DataHeaderConstants;
import c0d1red.infrastructure.FootballPredictionSystem;
import c0d1red.infrastructure.StatCalculator;
import c0d1red.infrastructure.utils.CSVUtil;
import org.nd4j.evaluation.classification.Evaluation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String CSV_FILE_PATH = "src/main/resources/RPL.csv";
    private static final String CSV_SEPARATOR = ";";

    public static void main(String[] args) {
        DataTable dataTable = CSVUtil.readData(CSV_FILE_PATH, CSV_SEPARATOR);
        Set<String> teams = new HashSet<>(dataTable.getColumnBy(DataHeaderConstants.TEAM_NAME));
        DataTable filteredTable = dataTable.getFilteredBy(DataHeaderConstants.RIVAL_TEAM_NAME, teams.toArray(new String[0]));

        StatCalculator statCalculator = new StatCalculator();
        FootballStatMap stats = statCalculator.calculateAllTeamsStat(filteredTable);

        FootballPredictionSystem footballPredictionSystem = new FootballPredictionSystem(stats.getSize());
        List<TrainObject> trainObjectList = footballPredictionSystem.createTestData(filteredTable, stats);
        Evaluation evaluation = footballPredictionSystem.train(trainObjectList);
        System.out.println(evaluation.stats());

        double predict_1 = footballPredictionSystem.predict(stats, "Зенит", "Мордовия");
        double predict_2 = footballPredictionSystem.predict(stats, "Мордовия", "Зенит");
        System.out.println();
        System.out.println("Вероятность победы Зенита: " + predict_1);
        System.out.println("Вероятность победы Мордовии: " + predict_2);
    }

}
