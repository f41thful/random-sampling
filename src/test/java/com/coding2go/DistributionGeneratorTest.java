package com.coding2go;

import com.coding2go.common.RealHelper;
import com.coding2go.exceptions.DistributionException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DistributionGeneratorTest {
    private static final Logger logger = Logger.getLogger(DistributionGeneratorTest.class);

    private static final RealHelper realHelper = new RealHelper();
    private DistributionGenerator distributionGenerator;
    private int numClasses = 5;

    @Before
    public void init () {
        distributionGenerator = new DistributionGenerator(numClasses);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenZero_thenRaisesException() {
        new DistributionGenerator(0);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenNegative_thenRaisesException() {
        new DistributionGenerator(-1);
    }

    @Test
    public void givenEquallyLikelyDistribution_thenCheckDistribution() {
        List<Double> distribution = new ArrayList<>();

        for(int i = 0; i < numClasses; i++) {
            distribution.add(distributionGenerator.getDistribution(i));
        }

        double freq = ((double) 1) / numClasses;

        for(Double actualFreq : distribution) {
            Assert.assertTrue(realHelper.equals(actualFreq, freq));
        }
    }

    @Test
    public void givenValidInput_thenValidDistribution() {
        List<Double> distribution = distributionGenerator.generate();

        assertEquals(numClasses, distribution.size());
    }

    @Test(expected = DistributionException.class)
    public void givenInvalidDistributionSumNot1_thenException() {
        DistributionGenerator distributionGenerator = new DistributionGenerator(numClasses) {
            @Override
            double getDistribution(int klass) {
                return 0;
            }
        };

        distributionGenerator.generate();
    }

    @Test(expected = DistributionException.class)
    public void givenInvalidDistributionSumNot1ByLittle_thenException() {
        DistributionGenerator distributionGenerator = new DistributionGenerator(3) {
            @Override
            double getDistribution(int klass) {
                return 0.33;
            }
        };

        distributionGenerator.generate();
    }

    @Test(expected = DistributionException.class)
    public void givenInvalidDistributionBecauseOfNegative_thenException() {
        DistributionGenerator distributionGenerator = new DistributionGenerator(3) {
            @Override
            double getDistribution(int klass) {
                switch (klass) {
                    case 0: return 1;
                    case 1: return -1;
                    case 2: return 1;
                }

                throw new InvalidParameterException();
            }
        };

        distributionGenerator.generate();
    }

    @Test
    public void givenValidDistributionSumNot1WithinBias_thenOk() {
        DistributionGenerator distributionGenerator = new DistributionGenerator(3) {
            @Override
            double getDistribution(int klass) {
                return 0.33333;
            }
        };

        assertEquals(3, distributionGenerator.generate().size());
    }

}