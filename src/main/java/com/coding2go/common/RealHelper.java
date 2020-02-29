package com.coding2go.common;

public class RealHelper {
    private static final double BIAS = 0.0001;

    public boolean equals(double n0, double n1) {
        return Math.abs(n0 - n1) <= BIAS;
    }
}
