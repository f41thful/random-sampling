package com.coding2go.runners;

import com.coding2go.Population;
import com.coding2go.common.ListHelper;
import com.coding2go.results.RepetitionExperimentResult;
import com.coding2go.results.SelectionExperimentResult;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RepetitionRunner {
    private static final Logger logger = Logger.getLogger(RepetitionRunner.class);
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
        RepetitionExperimentResult repetitionExperimentResult = new RepetitionExperimentResult(numTimes, bias, population);
        SelectionExperimentResult selectionExperimentResult;
        logger.info("Starts repetition experiment with " + numTimes + " iterations.");
        for(int i = 0; i < numTimes; i++) {
            logger.info("Starts iteration with index " + i);
            selectionExperimentResult = runSamplingExperiment();
            repetitionExperimentResult.addExperimentResult(selectionExperimentResult);

            boolean isLessOrEqualThanBias = isLessOrEqualThanBias(selectionExperimentResult);
            if(!isLessOrEqualThanBias) {
                logger.info("Iteration " + i + " is greater than the bias.");
                repetitionExperimentResult.incrementGreaterThanBias();
                if(stopFast) {
                    logger.info("stop fast is enabled, stopping the runner.");
                    break;
                }
            }
            logger.info("Finishes iteration with index " + i);
        }
        logger.info("Finishes repetition experiment with " + numTimes + " iterations.");

        repetitionExperimentResult.calculate();
        return repetitionExperimentResult;
    }

    private boolean isLessOrEqualThanBias(SelectionExperimentResult selectionExperimentResult) {
        List<Double> pDiff = listHelper.pDiff(selectionExperimentResult.getSelectionDistribution(), population.getClassDistribution());
        return listHelper.isLessOrEqual(pDiff, bias);
    }

    SelectionExperimentResult runSamplingExperiment() {
        return new SampleRunner(random, numSamples, population).run();
    }
}
