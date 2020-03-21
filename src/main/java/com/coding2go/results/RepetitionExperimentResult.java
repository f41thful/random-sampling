package com.coding2go.results;

import com.coding2go.Population;
import com.coding2go.common.ListHelper;
import com.coding2go.common.StatisticsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RepetitionExperimentResult {
    class Statistics {
        public List<Double> mean;
        public List<Double> std;

        public Statistics() {
            mean = new ArrayList<>();
            std = new ArrayList<>();
        }
    }
    private StatisticsHelper statisticsHelper;
    private ListHelper listHelper;

    private final int numTimes;
    private final double bias;
    private final Population population;
    private List<SelectionExperimentResult> experiments;

    private List<Double> meanSamplingDistribution;
    private List<Double> stdSamplingDistribution;

    private List<Double> meanRepetitionDistribution;
    private List<Double> stdRepetitionDistribution;

    private boolean isLessOrEqualTheBias;

    public RepetitionExperimentResult(int numTimes, double bias, Population population) {
        if (numTimes <= 0) {
            throw new IllegalArgumentException("numTimes must be > 0. It was " + numTimes + ".");
        }

        if (bias < 0) {
            throw new IllegalArgumentException("bias must be >= 0. It was " + bias + ".");
        }

        Objects.requireNonNull(population);

        this.numTimes = numTimes;
        this.bias = bias;
        this.population = population;

        init();
    }

    public void addExperimentResult(SelectionExperimentResult result) {
        experiments.add(result);
    }

    private void init() {
        experiments = new ArrayList<>();
        isLessOrEqualTheBias = true;
        statisticsHelper = new StatisticsHelper();
        listHelper = new ListHelper();
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

    public void calculate() {
        calculateSampling();
        calculateRepetition();
    }

    private void calculateSampling() {
        Statistics statistics = calculate(listHelper.reGroupByIndex(getSamplingDistributions()));
        this.meanSamplingDistribution = statistics.mean;
        this.stdSamplingDistribution = statistics.std;
    }

    private void calculateRepetition() {
        Statistics statistics = calculate(listHelper.reGroupByIndex(getRepetitionDistributions()));
        this.meanRepetitionDistribution = statistics.mean;
        this.stdRepetitionDistribution = statistics.std;
    }

    private Statistics calculate(List<List<Double>> valuesGrouped) {
        Statistics statistics = new Statistics();
        for(List<Double> values : valuesGrouped) {
            StatisticsHelper.Statistics statisticalValues = statisticsHelper.statistics(values);
            statistics.mean.add(statisticalValues.getMean());
            statistics.std.add(statisticalValues.getStd());
        }

        return statistics;
    }

    private List<List<Double>> getSamplingDistributions() {
        return experiments.stream()
                          .map(SelectionExperimentResult::getSelectionDistribution)
                          .collect(Collectors.toList());
    }

    private List<List<Double>> getRepetitionDistributions() {
        return experiments.stream()
                          .map(SelectionExperimentResult::getRepetitionDistribution)
                          .collect(Collectors.toList());
    }
}
