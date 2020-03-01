package com.coding2go;

import java.util.List;

public class SelectionExperimentResult {
    private final List<Double> selectionDistribution;
    private final List<Double> repetions;

    public SelectionExperimentResult(List<Double> selectionDistribution, List<Double> repetions) {
        this.selectionDistribution = selectionDistribution;
        this.repetions = repetions;
    }

    public List<Double> getSelectionDistribution() {
        return selectionDistribution;
    }

    public List<Double> getRepetions() {
        return repetions;
    }
}
