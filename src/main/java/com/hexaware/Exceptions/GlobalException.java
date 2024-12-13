package com.hexaware.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hexaware.Exceptions.EmployeeCustomExceptions.InvalidLeaveException;
import com.hexaware.Exceptions.EmployeeCustomExceptions.LeaveRequestFailedException;
import com.hexaware.Exceptions.EmployeeCustomExceptions.ProfileUpdateFailedException;
import com.hexaware.Exceptions.PayrollCustomExceptions.PayrollCreationFailedException;
import com.hexaware.Exceptions.PayrollCustomExceptions.PayrollNotFoundException;
import com.hexaware.Exceptions.PayrollCustomExceptions.PayrollUpdateFailedException;
import com.hexaware.Exceptions.UserCustomExceptions.DepartmentNotFoundException;
import com.hexaware.Exceptions.UserCustomExceptions.DuplicateUsernameException;
import com.hexaware.Exceptions.UserCustomExceptions.EmployeeNotFoundException;
import com.hexaware.Exceptions.UserCustomExceptions.InvalidLeaveApprovalException;
import com.hexaware.Exceptions.UserCustomExceptions.LeaveNotFoundException;
import com.hexaware.Exceptions.UserCustomExceptions.RoleNotFoundException;
import com.hexaware.Exceptions.UserCustomExceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(RoleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<String> handleDepartmentNotFound(DepartmentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LeaveNotFoundException.class)
    public ResponseEntity<String> handleLeaveNotFound(LeaveNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLeaveApprovalException.class)
    public ResponseEntity<String> handleInvalidLeaveApproval(InvalidLeaveApprovalException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeaveRequestFailedException.class)
    public ResponseEntity<String> handleLeaveRequestFailed(LeaveRequestFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLeaveException.class)
    public ResponseEntity<String> handleInvalidLeave(InvalidLeaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileUpdateFailedException.class)
    public ResponseEntity<String> handleProfileUpdateFailed(ProfileUpdateFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(PayrollNotFoundException.class)
    public ResponseEntity<String> handlePayrollNotFound(PayrollNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PayrollCreationFailedException.class)
    public ResponseEntity<String> handlePayrollCreationFailed(PayrollCreationFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PayrollUpdateFailedException.class)
    public ResponseEntity<String> handlePayrollUpdateFailed(PayrollUpdateFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

