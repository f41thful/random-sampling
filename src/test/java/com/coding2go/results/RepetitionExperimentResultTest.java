package com.coding2go.results;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RepetitionExperimentResultTest {
    private static final double BIAS = 0.05;

    private RepetitionExperimentResult repetitionExperimentResult;

    @Before
    public void init() {
        repetitionExperimentResult = new RepetitionExperimentResult(5, BIAS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidNumTimesV0_thenException() {
        new RepetitionExperimentResult(-1, BIAS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidNumTimesV1_thenException() {
        new RepetitionExperimentResult(0, BIAS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidBias_thenException() {
        new RepetitionExperimentResult(2, -0.5);
    }

    @Test
    public void givenResultsAreAdded_thenTheyAreReturned() {
        SelectionExperimentResult selectionExperimentResult0 = mock(SelectionExperimentResult.class);
        SelectionExperimentResult selectionExperimentResult1 = mock(SelectionExperimentResult.class);

        repetitionExperimentResult.addExperimentResult(selectionExperimentResult0);
        repetitionExperimentResult.addExperimentResult(selectionExperimentResult1);

        List<SelectionExperimentResult> experiments = repetitionExperimentResult.getExperiments();

        assertEquals(2, experiments.size());
        assertTrue(experiments.contains(selectionExperimentResult0));
        assertTrue(experiments.contains(selectionExperimentResult1));
    }
}