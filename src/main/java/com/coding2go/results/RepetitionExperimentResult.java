package com.coding2go.results;

import java.util.ArrayList;
import java.util.List;

public class RepetitionExperimentResult {
    private final int numTimes;
    private final double bias;
    private List<SelectionExperimentResult> experiments;

    private List<Double> meanSamplingDistribution;
    private List<Double> stdSamplingDistribution;

    private List<Double> meanRepetitionDistribution;
    private List<Double> stdRepetitionDistribution;

    private boolean isLessOrEqualTheBias;

    public RepetitionExperimentResult(int numTimes, double bias) {
        if (numTimes <= 0) {
            throw new IllegalArgumentException("numTimes must be > 0. It was " + numTimes + ".");
        }

        if (bias < 0) {
            throw new IllegalArgumentException("bias must be >= 0. It was " + bias + ".");
        }

        this.numTimes = numTimes;
        this.bias = bias;

        init();
    }

    public void addExperimentResult(SelectionExperimentResult result) {
        experiments.add(result);
    }

    private void init() {
        experiments = new ArrayList<>();
        isLessOrEqualTheBias = true;
    }

    public int getNumTimes() {
        return numTimes;
    }

    public double getBias() {
        return bias;
    }

    public List<SelectionExperimentResult> getExperiments() {
        return experiments;
    }

    public List<Double> getMeanSamplingDistribution() {
        return meanSamplingDistribution;
    }

    public List<Double> getStdSamplingDistribution() {
        return stdSamplingDistribution;
    }

    public List<Double> getMeanRepetitionDistribution() {
        return meanRepetitionDistribution;
    }

    public List<Double> getStdRepetitionDistribution() {
        return stdRepetitionDistribution;
    }

    public boolean isLessOrEqualTheBias() {return isLessOrEqualTheBias;}

    public void setGreaterThanBias() {
        isLessOrEqualTheBias = false;
    }
}
