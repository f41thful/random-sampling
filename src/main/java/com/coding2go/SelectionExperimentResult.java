package com.coding2go;

import java.util.List;

public class SelectionExperimentResult {
    private final Population population;
    private final List<Double> selectionDistribution;
    private final List<Double> repetitionDistribution;

    public SelectionExperimentResult(Population population, List<Double> selectionDistribution, List<Double> repetitionDistribution) {
        this.population = population;
        this.selectionDistribution = selectionDistribution;
        this.repetitionDistribution = repetitionDistribution;
    }

    public List<Double> getSelectionDistribution() {
        return selectionDistribution;
    }

    public List<Double> getRepetitionDistribution() {
        return repetitionDistribution;
    }

    public Population getPopulation() {
        return population;
    }
}
