package c0d1red.infrastructure;

import c0d1red.domain.DataTable;
import c0d1red.domain.FootballStatMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static c0d1red.infrastructure.DataHeaderConstants.*;

public class StatCalculator {
    private static final int WIN_POINT = 3;
    private static final String DRAW_RESULT = "Ничья";

    public FootballStatMap calculateAllTeamsStat(DataTable dataTable) {
        Set<String> teamNames = new HashSet<>(dataTable.getColumnBy(TEAM_NAME));
        return createStat(dataTable, teamNames);
    }

    private FootballStatMap createStat(DataTable dataTable, Set<String> teamNames) {
        FootballStatMap statMap = new FootballStatMap();
        teamNames.forEach(teamName -> {
            DataTable teamDataTable = dataTable.getFilteredBy(TEAM_NAME, teamName);
            List<Double> teamStat = calculateTeamStat(teamDataTable, teamName);
            statMap.put(teamName, teamStat);
        });
        return statMap;
    }

    private List<Double> calculateTeamStat(DataTable teamDataTable, String teamName) {
        long scoredGoals = calculateGoals(teamDataTable.getColumnBy(SCORED));
        long concededGoals = calculateGoals(teamDataTable.getColumnBy(CONCEDED));
        long wins = calculateWins(teamDataTable, teamName);
        long draws = calculateDraws(teamDataTable);
        long lost = calculateLost(teamDataTable, teamName);
        long score = calculateScore(wins, draws);
        long games = calculateSumGames(wins, draws, lost);
        long shots = calculateShots(teamDataTable);
        long targetShots = calculateTargetShots(teamDataTable);
        long crosses = calculateCrosses(teamDataTable);
        long accurateCrosses = calculateAccurateCrosses(teamDataTable);
        long passes = calculatePasses(teamDataTable);
        long accuratePasses = calculateAccuratePasses(teamDataTable);
        double handle = calculateHandle(teamDataTable);
        double xG = calculateXG(teamDataTable);
        double ppda = calculatePPDA(teamDataTable);
        return List.of(
                (double) scoredGoals,
                (double) concededGoals,
                (double) wins,
                (double) draws,
                (double) lost,
                (double) score,
                (double) games,
                (double) shots,
                (double) targetShots,
                (double) crosses,
                (double) accurateCrosses,
                (double) passes,
                (double) accuratePasses,
                handle,
                xG,
                ppda);
    }

    private int calculateGoals(List<String> goals) {
        return goals.stream()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private long calculateWins(DataTable teamDataTable, String teamName) {
        return teamDataTable.getColumnBy(WINNER_TEAM_NAME).stream()
                .filter(winTeamName -> winTeamName.equals(teamName))
                .count();
    }

    private long calculateLost(DataTable teamDataTable, String teamName) {
        return teamDataTable.getColumnBy(LOSER_TEAM_NAME).stream()
                .filter(winTeamName -> winTeamName.equals(teamName))
                .count();
    }

    private long calculateDraws(DataTable teamDataTable) {
        return teamDataTable.getColumnBy(WINNER_TEAM_NAME).stream()
                .filter(winTeamName -> winTeamName.equals(DRAW_RESULT))
                .count();
    }

    private long calculateScore(long wins, long draws) {
        long sumWinScore = wins * WIN_POINT;
        return sumWinScore + draws;
    }

    private long calculateSumGames(long wins, long draws, long lost) {
        return wins + draws + lost;
    }

    private long calculateShots(DataTable teamDataTable) {
        return sumLongValues(teamDataTable, SHOTS);
    }

    private long calculateTargetShots(DataTable teamDataTable) {
        return sumLongValues(teamDataTable, TARGET_SHOTS);
    }

    private long calculateCrosses(DataTable teamDataTable) {
        return sumLongValues(teamDataTable, CROSSES);
    }

    private long calculateAccurateCrosses(DataTable teamDataTable) {
        return sumLongValues(teamDataTable, ACCURATE_CROSSES);
    }

    private long calculatePasses(DataTable teamDataTable) {
        return sumLongValues(teamDataTable, PASSES);
    }

    private long calculateAccuratePasses(DataTable teamDataTable) {
        return sumLongValues(teamDataTable, ACCURATE_PASSES);
    }

    private double calculateXG(DataTable teamDataTable) {
        return sumDoubleValues(teamDataTable, XG);
    }

    private double calculateHandle(DataTable teamDataTable) {
        return sumDoubleValues(teamDataTable, BALL_POSSESSION);
    }

    private double calculatePPDA(DataTable teamDataTable) {
        return sumDoubleValues(teamDataTable, PPDA);
    }

    private long sumLongValues(DataTable teamDataTable, String columnName) {
        return teamDataTable.getColumnBy(columnName).stream()
                .mapToLong(Long::parseLong)
                .sum();
    }

    private double sumDoubleValues(DataTable teamDataTable, String columnName) {
        return teamDataTable.getColumnBy(columnName).stream()
                .mapToDouble(Double::parseDouble)
                .sum();
    }

}
