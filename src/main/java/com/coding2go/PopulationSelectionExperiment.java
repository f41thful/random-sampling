package com.coding2go;


import com.coding2go.common.RealHelper;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopulationSelectionExperiment {
    private static final Logger logger = Logger.getLogger(PopulationSelectionExperiment.class);
    private final RealHelper realHelper;

    private Population population;
    private int numSelections;

    private List<Double> absoluteKlassDistribution;
    private List<Double> absoluteNonSelectedIndividualsPerClass;
    private List<Integer> absoluteSelectionsPerClass;
    private List<Integer> absoluteRepetitionsPerClass;

    public PopulationSelectionExperiment(Population population) {
        realHelper = new RealHelper();
        this.population = population;

        createLists();
        initLists();

        logger.info("Created a population selection experiment with absolute class distribution: " + absoluteKlassDistribution);
    }

    public PopulationSelection select(double individual) {
        numSelections++;

        int klass = computeClassForIndividual(individual);
        boolean isRepetition = computeRepetitionFor(individual, klass);

        return new PopulationSelection(individual, klass, isRepetition);
    }

    private boolean computeRepetitionFor(double individual, int klass) {
        double absDistribution = absoluteKlassDistribution.get(klass);
        if(realHelper.equals(absDistribution, 0)) {
            throw new IllegalStateException("The absolute distribution for class " + klass + " is zero. A class with " +
                    "zero distribution should never be selected. Individual " + individual + " ,absolute distribution " + absoluteKlassDistribution);
        }

        double repetitionProb = 1 - absoluteNonSelectedIndividualsPerClass.get(klass) / absDistribution;
        boolean isRepeated = individual < repetitionProb;

        if(isRepeated) {
            incr(absoluteRepetitionsPerClass, klass, 1);
        } else {
            incr(absoluteNonSelectedIndividualsPerClass, klass, -1);
        }

        return isRepeated;
    }

    private int computeClassForIndividual(double i) {
        int klass = population.getClassForIndividual(i);

        incr(absoluteSelectionsPerClass, klass, 1);
        return klass;
    }

    public SelectionExperimentResult getResult() {
        List<Double> samplingDistribution = createSamplingDistribution();
        List<Double> samplingRepetition = createSamplingRepetition();
        return new SelectionExperimentResult(population, samplingDistribution, samplingRepetition);
    }

    public int getNumSelections() {
        return numSelections;
    }

    private void createLists() {
        absoluteKlassDistribution = new ArrayList<>();
        absoluteNonSelectedIndividualsPerClass = new ArrayList<>();
        absoluteSelectionsPerClass = new ArrayList<>();
        absoluteRepetitionsPerClass = new ArrayList<>();
    }

    private void initLists() {
        for(int i = 0; i < population.getNumDiffClasses(); i++) {
            double people = population.getPopulationForClass(i);
            absoluteKlassDistribution.add(people);
            absoluteNonSelectedIndividualsPerClass.add(people);
            absoluteSelectionsPerClass.add(0);
            absoluteRepetitionsPerClass.add(0);
        }

        absoluteKlassDistribution = Collections.unmodifiableList(absoluteKlassDistribution);
    }

    private void incr(List<Integer> list, int index, int amount) {
        if(list == null || index >= list.size()) {
            throw new InvalidParameterException("Cannot access index " + index + " of list " + list);
        }

        list.set(index, list.get(index) + amount);
    }

    private void incr(List<Double> list, int index, double amount) {
        if(list == null || index >= list.size()) {
            throw new InvalidParameterException("Cannot access index " + index + " of list " + list);
        }

        list.set(index, list.get(index) + amount);
    }

    private List<Double> createSamplingDistribution() {
        List<Double> samplingDistribution = new ArrayList<>();
        if(numSelections == 0) {
            samplingDistribution.add(0.0);
        } else {
            for (int absoluteSelection : absoluteSelectionsPerClass) {
                samplingDistribution.add(((double) absoluteSelection) / numSelections);
            }
        }

        return samplingDistribution;
    }

    private List<Double> createSamplingRepetition() {
        List<Double> samplingRepetition = new ArrayList<>();

        for(int i = 0; i < absoluteRepetitionsPerClass.size(); i++) {
            if(absoluteSelectionsPerClass.get(i) == 0) {
                samplingRepetition.add(0.0);
            } else {
                samplingRepetition.add(((double) absoluteRepetitionsPerClass.get(i)) / absoluteSelectionsPerClass.get(i));
            }
        }

        return samplingRepetition;
    }
}
