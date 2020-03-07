package com.coding2go;

import java.util.List;

public class SelectionExperimentResult {
    private final List<Double> selectionDistribution;
    private final List<Double> repetitions;

    public SelectionExperimentResult(List<Double> selectionDistribution, List<Double> repetitions) {
        this.selectionDistribution = selectionDistribution;
        this.repetitions = repetitions;
    }

    public List<Double> getSelectionDistribution() {
        return selectionDistribution;
    }

    public List<Double> getRepetitions() {
        return repetitions;
    }
}
