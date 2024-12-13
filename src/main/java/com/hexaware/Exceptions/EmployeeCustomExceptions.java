package com.hexaware.Exceptions;

public class EmployeeCustomExceptions {

	public static class EmployeeNotFoundException extends RuntimeException {
	    public EmployeeNotFoundException(String message) {
	        super(message);
	    }
	}

	public static class LeaveRequestFailedException extends RuntimeException {
	    public LeaveRequestFailedException(String message) {
	        super(message);
	    }
	}

	public static class InvalidLeaveException extends RuntimeException {
	    public InvalidLeaveException(String message) {
	        super(message);
	    }
	}

	public static class ProfileUpdateFailedException extends RuntimeException {
	    public ProfileUpdateFailedException(String message) {
	        super(message);
	    }
	}
}
