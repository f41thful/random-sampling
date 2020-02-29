package com.coding2go;

import com.coding2go.exceptions.DistributionException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

public class Distribution {
    private int populationSize;
    private List<Double> classFreq;

    public Distribution(int populationSize, List<Double> classFreq) {
        Objects.requireNonNull(classFreq);
        if(classFreq.isEmpty()) {
            throw new DistributionException("The distribution frequencies must have at least one frequency.");
        }

        if(populationSize <= 0) {
            throw new InvalidParameterException("The population size must be positibe but is " + populationSize);
        }

        this.populationSize = populationSize;
        this.classFreq = classFreq;
    }
}
