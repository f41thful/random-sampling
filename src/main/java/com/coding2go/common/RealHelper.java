package com.coding2go.common;

public class RealHelper {
    private static final double BIAS = 0.0001;
    private static final RealHelper realHelper = new RealHelper();

    public boolean equals(double n0, double n1) {
        return Math.abs(n0 - n1) <= BIAS;
    }

    public Double pDiff(double value, double reference) {
        if (realHelper.equals(reference, 0)) {
            return null;
        } else {
            return (value - reference) / reference;
        }
    }
}
