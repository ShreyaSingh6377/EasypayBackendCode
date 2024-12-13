package com.hexaware.ServiceInterface;

import java.util.List;

import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Leaves;

public interface EmployeeInterface {

    EmployeeDTO updatePersonalInfo(int id, Employee updatedInfo);

    Leaves requestLeave(int employeeId, Leaves leaveRequest);

    EmployeeDTO getEmployeeById(int id);

	EmployeeDTO getEmployeeByFirstName(String username);

	List<Leaves> getLeavesByEmployee(int empId);
}
