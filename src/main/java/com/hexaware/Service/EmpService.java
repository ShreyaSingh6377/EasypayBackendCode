package com.hexaware.Service;

import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Leaves;
import com.hexaware.Entity.Leaves.LeaveStatus;
import com.hexaware.Exceptions.EmployeeCustomExceptions.EmployeeNotFoundException;
import com.hexaware.Repository.EmployeeRepo;
import com.hexaware.Repository.LeaveRepo;
import com.hexaware.ServiceInterface.EmployeeInterface;
import com.hexaware.DTO.EmployeeDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpService implements EmployeeInterface {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private LeaveRepo leaveRepository;

    // Method to update personal information of an employee
    @Override
    public EmployeeDTO updatePersonalInfo(int id, Employee updatedInfo) {
        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent()) {
            Employee existingEmployee = emp.get();

            existingEmployee.setFirstName(updatedInfo.getFirstName());
            existingEmployee.setLastName(updatedInfo.getLastName());
            existingEmployee.setEmail(updatedInfo.getEmail());
            existingEmployee.setPhoneNumber(updatedInfo.getPhoneNumber());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return convertToDTO(updatedEmployee);
        } else {
            return null;
        }
    }

    // Method to request leave for an employee
    @Override
    public Leaves requestLeave(int employeeId, Leaves leaveRequest) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            leaveRequest.setEmployee(optionalEmployee.get());

            // Ensure status is set before saving
            if (leaveRequest.getStatus() == null) {
                leaveRequest.setStatus(LeaveStatus.PENDING);  // Default to PENDING status if not provided
            }

            return leaveRepository.save(leaveRequest);
        } else {
            return null;
        }
    }

    // Method to view profile of an employee
    public EmployeeDTO getEmployeeByFirstName(String firstName) {
        Employee employee = employeeRepository.findByFirstName(firstName)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with username: " + firstName));
        return convertToDTO(employee); // Assuming you have a conversion method
    }
    
    public List<Leaves> getLeavesByEmployee(int empId) {
        return leaveRepository.findByEmployee_EmpId(empId);
    }


    // Private method to convert Employee to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getHireDate(),
                employee.getSalary(),
                employee.getDepartment().getDeptId(),
                employee.getUser().getUserId()
        );
    }

	@Override
	public EmployeeDTO getEmployeeById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
