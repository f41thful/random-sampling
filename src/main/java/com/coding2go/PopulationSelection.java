package com.coding2go;

import java.security.InvalidParameterException;

public class PopulationSelection {
    private final double individual;
    private final int klass;
    private final boolean hasAlreadyBeenSelected;

    public PopulationSelection(double individual, int klass, boolean selectedOnly1Time) {
        if(individual < 0 || individual > 1) {
            throw new InvalidParameterException("Individual must be within the range [0-1]. Provided " + individual + "." );
        }

        if(klass < 0) {
            throw new InvalidParameterException("Class must be natural. Provided " + klass + ".");
        }

        this.individual = individual;
        this.klass = klass;
        this.hasAlreadyBeenSelected = selectedOnly1Time;
    }

    public double getIndividual() {
        return individual;
    }

    public int getKlass() {
        return klass;
    }

    public boolean hasAlreadyBeenSelected() {
        return hasAlreadyBeenSelected;
    }
}
