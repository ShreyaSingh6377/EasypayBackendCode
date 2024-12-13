package com.hexaware.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.Entity.Payroll;

public interface PayrollRepo extends JpaRepository<Payroll, Integer>{
	
    Payroll findByPayrollId(int payrollId);

    List<Payroll> findByEmployee_EmpId(int employeeId);
}