package c0d1red.domain;

import java.util.List;

public class TrainObject {
    private final List<Double> inputs;
    private final Integer output;

    public TrainObject(List<Double> inputs, Integer output) {
        this.inputs = inputs;
        this.output = output;
    }

    public List<Double> getInputs() {
        return inputs;
    }

    public Integer getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "TrainObject{" +
                "inputs=" + inputs +
                ", output=" + output +
                '}';
    }
}
