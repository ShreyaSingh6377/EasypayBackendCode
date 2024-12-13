package com.hexaware.Service;

import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.DTO.UserDTO;
import com.hexaware.Entity.Department;
import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Leaves;
import com.hexaware.Entity.Users;
import com.hexaware.Entity.Users.Role;
import com.hexaware.Repository.DepartmentRepo;
import com.hexaware.Repository.EmployeeRepo;
import com.hexaware.Repository.LeaveRepo;
import com.hexaware.Repository.UserRepo;
import com.hexaware.ServiceInterface.UserInterface;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface{

    @Autowired
    UserRepo rep;
    
    @Autowired
    EmployeeRepo employeeRepo;
    
    @Autowired
    DepartmentRepo departmentRepo;
    
    @Autowired
    LeaveRepo leaveRepo;
    
    @Autowired
	JWTService service;
	
	@Autowired
	AuthenticationManager authManager;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    // admin: add a new user
    public UserDTO admin_addUser(UserDTO userDTO) {
        Users user = new Users(userDTO.getUsername(), userDTO.getPassword(), Users.Role.valueOf(userDTO.getRole()));
        user.setPassword(encoder.encode(user.getPassword()));
        Users savedUser = rep.save(user);
        return convertToDTO(savedUser);
    }

    // Admin: Edit user name
    public String admin_editUserName(String currentUsername, String newUsername) {
        Users user = rep.findByUsername(currentUsername);
        if (user == null) {
            return "User not found";
        }
        if (rep.findByUsername(newUsername) != null) {
            return "Username already exists";
        }
        user.setUsername(newUsername);
        rep.save(user);
        return "Username updated.";
    }

    // Admin: Edit user role
    public String admin_editUserRole(String currentUsername, Role newRole) {
        Users user = rep.findByUsername(currentUsername);
        if (user == null) {
            return "User not found";
        }
        user.setRole(newRole);
        rep.save(user);
        return "User role updated.";
    }

    // Admin: Edit user password
    public String admin_editUserPwd(String currentUsername, String pwd) {
        Users user = rep.findByUsername(currentUsername);
        if (user == null) {
            return "User not found";
        }
        
        user.setPassword(encoder.encode(pwd));
        rep.save(user);
        return "User password updated.";
    }


    // admin: get info of all users
    public List<UserDTO> admin_showAll() {
        List<Users> users = rep.findAll();
        return users.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
    }

    // admin: get info of a specific user by userId
    public UserDTO admin_getDataById(int userID) {
        Users user = rep.findById(userID).orElse(null);
        return (user != null) ? convertToDTO(user) : null;
    }

    // admin: remove user
    public String admin_removeUser(int userID) {
        Users user = rep.findById(userID).orElse(null);

        if (user == null) {
            return "User does not exist.";
        }

        rep.deleteById(userID);
        return "User removed successfully";
    }
    
    
    // Method to update employee salary
    public EmployeeDTO updateEmployeeSalary(int employeeId, double newSalary) {
        Employee employee = employeeRepo.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        employee.setSalary(newSalary);
        Employee updatedEmployee = employeeRepo.save(employee);
        return convertToDTO(updatedEmployee);
    }

    // Method to fetch employee leave records
    public List<Leaves> getEmployeeLeaves(int employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        return leaveRepo.findByEmployee(employee.getEmpId());
    }

    // Method to approve or reject leave requests
    public Leaves approveRejectLeaveRequest(int leaveId, boolean approved) {
        Leaves leave = leaveRepo.findById(leaveId).orElse(null);
        if (leave == null) {
            throw new RuntimeException("Leave request not found");
        }

        if (approved) {
            leave.approve(); 
        } else {
            leave.reject(); 
        }

        return leaveRepo.save(leave);
    }


    // Method to get employees by department
    public List<EmployeeDTO> getEmployeesByDepartment(int departmentId) {
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        List<Employee> employees = employeeRepo.findByDepartment(department.getDeptId());
        return employees.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
    }

    // Method to convert Users entity to UserDTO
    private UserDTO convertToDTO(Users user) {
        return new UserDTO(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole().name());
    }
    
    //Method to enter employee details
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        
        Employee employee = convertToEntity(employeeDTO);

        Employee savedEmployee = employeeRepo.save(employee);

        return convertToDTO(savedEmployee);
    }
    
    //Method to get all employees
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();

        return employees.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
    }
    
    //Verify
    public String verify(Users u) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return service.generateToken(userDetails);
        }

        throw new RuntimeException("Authentication failed");
    }
    
    public String getRole(String username) {
        Users user = rep.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.getRole().name();
    }
    
    public List<Leaves> getAllLeaves() {
        return leaveRepo.findAll();
    }
    
    public long getTotalEmployees() {
        return rep.countTotalEmployees();
    }
    
    public long getTotalPayrollManager() {
        return rep.countTotalPayrollManager();
    }
    
    public long getTotalAdmin() {
        return rep.countTotalAdmin();
    }


    // Method to convert EmployeeDTO to Employee entity
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Department department = departmentRepo.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Users user = rep.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee employee = new Employee();
        employee.setEmpId(employeeDTO.getEmpId()); 
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(department);
        employee.setUser(user);

        return employee;
    }

    // Method to convert Employee entity to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employee.getEmpId()); 
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setHireDate(employee.getHireDate());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setDepartmentId(employee.getDepartment().getDeptId());
        employeeDTO.setUserId(employee.getUser().getUserId());

        return employeeDTO;
    }
    
    public Users getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from the Authentication object
        return rep.findByUsername(username); // Use your method to retrieve the user by username
    }
    
    public void updateEmployeeDepartment(int employeeId, int departmentId) {
        // Fetch employee by ID
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + " not found."));

        // Fetch department by ID
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department with ID " + departmentId + " not found."));

        // Update the department
        employee.setDepartment(department);
        employeeRepo.save(employee); // Save updated employee
    }
    
}