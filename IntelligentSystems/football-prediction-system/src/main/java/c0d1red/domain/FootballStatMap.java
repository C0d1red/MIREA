package c0d1red.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FootballStatMap {
    private final Map<String, List<Double>> statMap = new HashMap<>();

    public void put(String teamName, List<Double> stats) {
        statMap.put(teamName, stats);
    }

    public List<Double> getStatFor(String teamName) {
        return statMap.get(teamName);
    }

    public int getSize() {
        Map.Entry<String, List<Double>> entry = statMap.entrySet().iterator().next();
        return entry.getValue().size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Double>> entry : statMap.entrySet()) {
            String teamName = entry.getKey();
            List<Double> stat = entry.getValue();
            result.append(teamName).append(": ").append(stat);
        }
        return result.toString();
    }

}
