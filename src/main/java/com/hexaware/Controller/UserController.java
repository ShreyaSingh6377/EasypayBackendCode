package com.hexaware.Controller;

import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.DTO.UserDTO;
import com.hexaware.Entity.Leaves;
import com.hexaware.Entity.Users;
import com.hexaware.Exceptions.EmployeeCustomExceptions.EmployeeNotFoundException;
import com.hexaware.Exceptions.UserCustomExceptions.DuplicateUsernameException;
import com.hexaware.Exceptions.UserCustomExceptions.UserNotFoundException;
import com.hexaware.Service.UserService;
import com.hexaware.ServiceInterface.UserInterface;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserInterface ser;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> admin_addUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = ser.admin_addUser(userDTO); 
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users u) {
        String token = ser.verify(u);
        String role = ser.getRole(u.getUsername());
        Map<String, String> response = new HashMap<>();
        response.put("jwt", token);
        response.put("role", role);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/admin_editUsername/{username}/{newname}")
    public ResponseEntity<String> admin_editUserName(@PathVariable String username, @PathVariable String newname) {
        String result = ser.admin_editUserName(username, newname);
        if (result.equals("Username already exists")) {
            throw new DuplicateUsernameException("Username '" + newname + "' already exists");
        }
        if (result.equals("User not found")) {
            throw new UserNotFoundException("User with username '" + username + "' not found");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/admin_editUserRole/{username}/{role}")
    public ResponseEntity<String> admin_editUserRole(@PathVariable String username, @PathVariable String role) throws RoleNotFoundException {
        try {
            Users.Role newRole = Users.Role.valueOf(role.toUpperCase());
            String result = ser.admin_editUserRole(username, newRole);
            if (result.equals("User not found")) {
                throw new UserNotFoundException("User with username '" + username + "' not found");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException("Role '" + role + "' is invalid");
        }
    }

    @PostMapping("/admin_editUserPwd/{username}/{pwd}")
    public ResponseEntity<String> admin_editUserPwd(@PathVariable String username, @PathVariable String pwd) {
        String result = ser.admin_editUserPwd(username, pwd);
        if (result.equals("User not found")) {
            throw new UserNotFoundException("User with username '" + username + "' not found");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin_getUserData")
    public ResponseEntity<List<UserDTO>> getData() {
        List<UserDTO> userDTOList = ser.admin_showAll();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/admin_getUserData/{userID}")
    public ResponseEntity<UserDTO> getById(@PathVariable int userID) {
        UserDTO userDTO = ser.admin_getDataById(userID);
        if (userDTO == null) {
            throw new UserNotFoundException("User with ID '" + userID + "' not found");
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin_removeUser/{userID}")
    public ResponseEntity<String> admin_removeUser(@PathVariable int userID) {
        String result = ser.admin_removeUser(userID);
        if (result.equals("User not found")) {
            throw new UserNotFoundException("User with ID '" + userID + "' not found");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/admin_addEmployeeDetails")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO newEmployeeDTO = ser.createEmployee(employeeDTO);
        return new ResponseEntity<>(newEmployeeDTO, HttpStatus.CREATED);
    }
    
    @GetMapping("/admin_getAllEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = ser.getAllEmployees();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @PutMapping("/admin_updateEmployeeSalary/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeSalary(@PathVariable int employeeId, @RequestParam double newSalary) {
        EmployeeDTO updatedEmployee = ser.updateEmployeeSalary(employeeId, newSalary);
        if (updatedEmployee == null) {
            throw new UserNotFoundException("Employee with ID '" + employeeId + "' not found");
        }
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @GetMapping("/admin_getEmployeeLeaves/{employeeId}")
    public ResponseEntity<List<Leaves>> getEmployeeLeaves(@PathVariable int employeeId) {
        List<Leaves> leaves = ser.getEmployeeLeaves(employeeId);
        if (leaves == null || leaves.isEmpty()) {
            throw new UserNotFoundException("Employee with ID '" + employeeId + "' not found or has no leave records");
        }
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    @PutMapping("/admin_approveRejectLeave/{leaveId}")
    public ResponseEntity<Leaves> approveRejectLeaveRequest(@PathVariable int leaveId, @RequestParam boolean approved) {
        Leaves updatedLeave = ser.approveRejectLeaveRequest(leaveId, approved);
        if (updatedLeave == null) {
            throw new UserNotFoundException("Leave request with ID '" + leaveId + "' not found");
        }
        return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
    }

    @GetMapping("/admin_getEmployeesByDepartment/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable int departmentId) {
        List<EmployeeDTO> employees = ser.getEmployeesByDepartment(departmentId);
        if (employees.isEmpty()) {
            throw new UserNotFoundException("No employees found in department with ID '" + departmentId + "'");
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    
    @GetMapping("/admin_getAllLeaves")
    public ResponseEntity<List<Leaves>> getAllLeaves() {
        List<Leaves> leaves = ser.getAllLeaves();
        if (leaves.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(leaves);
    }
    
    @GetMapping("/admin_total_employees")
    public long getTotalEmployees() {
        return ser.getTotalEmployees();
    }
    
    @GetMapping("/admin_total_payroll_manager")
    public long getTotalPayrollManager() {
        return ser.getTotalPayrollManager();
    }
    
    @GetMapping("/admin_total_admin")
    public long getTotalAdmin() {
        return ser.getTotalAdmin();
    }
    
    @PutMapping("/admin_update_department/{id}")
    public ResponseEntity<String> updateEmployeeDepartment(
            @PathVariable int id, 
            @RequestParam int departmentId) {
        try {
            ser.updateEmployeeDepartment(id, departmentId);
            return ResponseEntity.ok("Employee department updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the department.");
        }
    }
}
