package com.coding2go.runners;

import com.coding2go.Population;
import com.coding2go.common.ListHelper;
import com.coding2go.results.RepetitionExperimentResult;
import com.coding2go.results.SelectionExperimentResult;

import java.util.Objects;
import java.util.Random;

public class RepetitionRunner {
    private final ListHelper listHelper;
    private final int numTimes;
    private final double bias;
    private final boolean stopFast;
    private final Random random;
    private final int numSamples;
    private final Population population;

    public RepetitionRunner(int numTimes, double bias, boolean stopFast, Random random, int numSamples, Population population) {
        if(numTimes <= 0) {
            throw new IllegalArgumentException("numTimes should be > 0. It is " + numTimes + ".");
        }

        if(numSamples <= 0) {
            throw new IllegalArgumentException("numSamples should be > 0. It is " + numSamples + ".");
        }

        if(bias < 0) {
            throw new IllegalArgumentException("bias should be >= 0. It is " + bias + ".");
        }

        Objects.requireNonNull(random);
        Objects.requireNonNull(population);

        this.numTimes = numTimes;
        this.bias = bias;
        this.stopFast = stopFast;
        this.random = random;
        this.numSamples = numSamples;
        this.population = population;

        listHelper = new ListHelper();
    }

    public RepetitionExperimentResult run() {
        RepetitionExperimentResult repetitionExperimentResult = new RepetitionExperimentResult(numTimes, bias);
        SelectionExperimentResult selectionExperimentResult;
        for(int i = 0; i < numTimes; i++) {
            selectionExperimentResult = runSamplingExperiment();
            repetitionExperimentResult.addExperimentResult(selectionExperimentResult);

            boolean isLessOrEqualThanBias = listHelper.isLessOrEqual(selectionExperimentResult.getSelectionDistribution(), bias);
            if(!isLessOrEqualThanBias) {
                repetitionExperimentResult.setGreaterThanBias();
                if(stopFast) {
                    break;
                }
            }
        }

        return repetitionExperimentResult;
    }

    SelectionExperimentResult runSamplingExperiment() {
        return new SampleRunner(random, numSamples, population).run();
    }
}
