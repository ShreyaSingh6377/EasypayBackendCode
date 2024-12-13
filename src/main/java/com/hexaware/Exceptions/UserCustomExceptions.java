package com.hexaware.Exceptions;

public class UserCustomExceptions {
	
	// Make each exception class static
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class DuplicateUsernameException extends RuntimeException {
        public DuplicateUsernameException(String message) {
            super(message);
        }
    }
    
    public static class RoleNotFoundException extends RuntimeException {
        public RoleNotFoundException(String message) {
            super(message);
        }
    }

	public class EmployeeNotFoundException extends RuntimeException {
	    public EmployeeNotFoundException(String message) {
	        super(message);
	    }
	}

	public class DepartmentNotFoundException extends RuntimeException {
	    public DepartmentNotFoundException(String message) {
	        super(message);
	    }
	}

	public class LeaveNotFoundException extends RuntimeException {
	    public LeaveNotFoundException(String message) {
	        super(message);
	    }
	}

	public class InvalidLeaveApprovalException extends RuntimeException {
	    public InvalidLeaveApprovalException(String message) {
	        super(message);
	    }
	}

}
