package com.hexaware.ServiceInterface;

import java.util.List;

import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.DTO.UserDTO;
import com.hexaware.Entity.Leaves;
import com.hexaware.Entity.Users;

public interface UserInterface {


    UserDTO admin_addUser(UserDTO userDTO);

    String admin_editUserName(String currentUsername, String newUsername);

    String admin_editUserRole(String currentUsername, Users.Role newRole);

    String admin_editUserPwd(String currentUsername, String pwd);

    List<UserDTO> admin_showAll();

    UserDTO admin_getDataById(int userID);

    String admin_removeUser(int userID);

    EmployeeDTO updateEmployeeSalary(int employeeId, double newSalary);

    List<Leaves> getEmployeeLeaves(int employeeId);

    Leaves approveRejectLeaveRequest(int leaveId, boolean approved);

    List<EmployeeDTO> getEmployeesByDepartment(int departmentId);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployees();

	String verify(Users u);

	String getRole(String username);

	long getTotalEmployees();

	long getTotalPayrollManager();

	long getTotalAdmin();

	List<Leaves> getAllLeaves();

	void updateEmployeeDepartment(int id, int departmentId);

}
