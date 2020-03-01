package com.coding2go.common;

import com.coding2go.exceptions.DistributionException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DistributionValidationTest {
    private DistributionValidation distributionValidation;
    private List<Double> distribution;

    @Before
    public void init() {
        distributionValidation = new DistributionValidation();
        distribution = new ArrayList<>();
    }

    @Test(expected = Exception.class)
    public void givenNullDistribution_thenThrowsException() {
        distributionValidation.validate(null);
    }

    @Test(expected = DistributionException.class)
    public void givenEmptyDistribution_thenThrowsException() {
        distributionValidation.validate(new ArrayList<>());
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionNotSumming1_thenDistributionException() {
        distribution.add(0.23);
        distributionValidation.validate(distribution);
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionNotSumming1ByLittle_thenDistributionException() {
        distribution.add(0.20);
        distribution.add(0.799);
        distributionValidation.validate(distribution);
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionNotSumming1ByLittleAbove_thenDistributionException() {
        distribution.add(0.20);
        distribution.add(0.801);
        distributionValidation.validate(distribution);
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionNotSumming1ByAbove_thenDistributionException() {
        distribution.add(0.20);
        distribution.add(0.99);
        distributionValidation.validate(distribution);
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionWithNegatives_thenDistributionException() {
        distribution.add(-0.3);
        distributionValidation.validate(distribution);
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionWithNegativesSumming1_thenDistributionException() {
        distribution.add(-0.3);
        distribution.add(1.3);
        distributionValidation.validate(distribution);
    }

    @Test(expected = DistributionException.class)
    public void givenDistributionWithNegativesNotSumming1_thenDistributionException() {
        distribution.add(-0.3);
        distribution.add(0.5);
        distributionValidation.validate(distribution);
    }

    @Test
    public void givenDistributionSumming1_thenOk() {
        distribution.add(0.3);
        distribution.add(0.7);
        distributionValidation.validate(distribution);
    }

    @Test
    public void givenDistributionSumming1ByLittle_thenOk() {
        distribution.add(0.3);
        distribution.add(0.700001);
        distributionValidation.validate(distribution);
    }
}