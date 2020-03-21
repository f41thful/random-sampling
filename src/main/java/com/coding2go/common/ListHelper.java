package com.coding2go.common;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListHelper {
    private static final Logger logger = Logger.getLogger(ListHelper.class);
    private static final RealHelper realHelper = new RealHelper();

    public List<Double> pDiff(List<Double> value, List<Double> reference) {
        List<Double> pDiff = new ArrayList<>();

        Objects.requireNonNull(value);
        Objects.requireNonNull(reference);

        if(value.size() != reference.size()) {
            throw new IllegalArgumentException("Arrays don't have the same size. Value size is " + value.size() + ", reference size is " + reference.size() + ".");
        }

        for(int i = 0; i < value.size(); i++) {
            Double valueValue = value.get(i);
            Double referenceValue = reference.get(i);

            if(valueValue == null || referenceValue == null) {
                logger.debug("Setting null in pDiff list for index " + i + " because either value or reference or both are null.");
                pDiff.add(null);
            } else {
                pDiff.add(realHelper.pDiff(valueValue, referenceValue));
            }
        }

        return pDiff;
    }
}
