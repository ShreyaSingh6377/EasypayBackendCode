package com.hexaware.Exceptions;

public class PayrollCustomExceptions {

	public static class PayrollNotFoundException extends RuntimeException {
	    public PayrollNotFoundException(String message) {
	        super(message);
	    }
	}

	public static class PayrollCreationFailedException extends RuntimeException {
	    public PayrollCreationFailedException(String message) {
	        super(message);
	    }
	}

	public static class PayrollUpdateFailedException extends RuntimeException {
	    public PayrollUpdateFailedException(String message) {
	        super(message);
	    }
	}
}
