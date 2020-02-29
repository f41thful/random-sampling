package com.coding2go.exceptions;

public class DistributionException extends RuntimeException{
    public DistributionException() {
        super();
    }

    public DistributionException(String message) {
        super(message);
    }

    public DistributionException(String message, Throwable cause) {
        super(message, cause);
    }
}
