package com.hexaware.Controller;

import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Leaves;
import com.hexaware.Exceptions.EmployeeCustomExceptions.EmployeeNotFoundException;
import com.hexaware.Exceptions.EmployeeCustomExceptions.LeaveRequestFailedException;
import com.hexaware.Service.EmpService;
import com.hexaware.ServiceInterface.EmployeeInterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class EmpController {

    @Autowired
    EmployeeInterface ser;

    @PutMapping("/emp/update_info/{id}")
    public ResponseEntity<EmployeeDTO> updatePersonalInfo(@PathVariable int id, @RequestBody Employee updatedInfo) {
        EmployeeDTO updatedEmployeeDTO = ser.updatePersonalInfo(id, updatedInfo);
        if (updatedEmployeeDTO != null) {
            return new ResponseEntity<>(updatedEmployeeDTO, HttpStatus.OK);
        } else {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
    }

    @PostMapping("/emp/leave_request/{id}")
    public ResponseEntity<Leaves> requestLeave(@PathVariable int id, @RequestBody Leaves leaveRequest) {
        Leaves submittedLeaveRequest = ser.requestLeave(id, leaveRequest);
        if (submittedLeaveRequest != null) {
            return new ResponseEntity<>(submittedLeaveRequest, HttpStatus.CREATED);
        } else {
            throw new LeaveRequestFailedException("Failed to submit leave request for Employee with ID " + id);
        }
    }
    
    
    @GetMapping("/emp/profile")
    public ResponseEntity<EmployeeDTO> viewProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if (username == null) {
            throw new EmployeeNotFoundException("User is not authenticated");
        }

        EmployeeDTO employeeDTO = ser.getEmployeeByFirstName(username);
        if (employeeDTO != null) {
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } else {
            throw new EmployeeNotFoundException("Employee not found with username: " + username);
        }
    }
    
    @GetMapping("/emp/leaves/{empId}")
    public List<Leaves> getLeavesByEmployee(@PathVariable int empId) {
        return ser.getLeavesByEmployee(empId);
    }



}
