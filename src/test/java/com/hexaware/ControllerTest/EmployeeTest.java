package com.hexaware.ControllerTest;

import com.hexaware.Controller.EmpController;
import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Leaves;
import com.hexaware.Exceptions.EmployeeCustomExceptions.EmployeeNotFoundException;
import com.hexaware.Exceptions.EmployeeCustomExceptions.LeaveRequestFailedException;
import com.hexaware.ServiceInterface.EmployeeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class EmployeeTest {

    @InjectMocks
    private EmpController empController;

    @Mock
    private EmployeeInterface employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdatePersonalInfo_Success() {
        // Arrange
        int employeeId = 1;
        Employee updatedInfo = new Employee();
        updatedInfo.setFirstName("John");
        updatedInfo.setLastName("Doe");
        updatedInfo.setEmail("john.doe@example.com");
        updatedInfo.setPhoneNumber("1234567890");

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setEmpId(employeeId);
        updatedEmployeeDTO.setFirstName("John");
        updatedEmployeeDTO.setLastName("Doe");

        when(employeeService.updatePersonalInfo(employeeId, updatedInfo)).thenReturn(updatedEmployeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = empController.updatePersonalInfo(employeeId, updatedInfo);

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedEmployeeDTO, response.getBody());
        verify(employeeService, times(1)).updatePersonalInfo(employeeId, updatedInfo);
    }

    @Test
    void testUpdatePersonalInfo_EmployeeNotFound() {
        // Arrange
        int employeeId = 2;
        Employee updatedInfo = new Employee();

        when(employeeService.updatePersonalInfo(employeeId, updatedInfo)).thenReturn(null);

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> empController.updatePersonalInfo(employeeId, updatedInfo));
        verify(employeeService, times(1)).updatePersonalInfo(employeeId, updatedInfo);
    }

    @Test
    void testRequestLeave_LeaveRequestFailed() {
        // Arrange
        int employeeId = 2;
        Leaves leaveRequest = new Leaves();

        when(employeeService.requestLeave(employeeId, leaveRequest)).thenReturn(null);

        // Act & Assert
        assertThrows(LeaveRequestFailedException.class, () -> empController.requestLeave(employeeId, leaveRequest));
        verify(employeeService, times(1)).requestLeave(employeeId, leaveRequest);
    }

    @Test
    void testViewProfile_Success() {
        // Arrange
        int employeeId = 1;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employeeId);
        employeeDTO.setFirstName("Jane");
        employeeDTO.setLastName("Doe");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = empController.viewProfile();

        // Assert
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void testViewProfile_EmployeeNotFound() {
        // Arrange
        int employeeId = 3;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(null);

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> empController.viewProfile());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }
}
