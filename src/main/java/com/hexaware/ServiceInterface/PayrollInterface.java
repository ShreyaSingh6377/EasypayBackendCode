package com.hexaware.ServiceInterface;

import java.util.List;

import com.hexaware.DTO.PayrollDTO;
import com.hexaware.Entity.Audit;
import com.hexaware.Entity.Payroll;

public interface PayrollInterface {

    void removePayroll(int payrollId);

    Payroll updatePayroll(int payrollId, PayrollDTO updatedPayrollDTO);

    Payroll calculatePayroll(PayrollDTO payrollDTO);

    Payroll findByPayrollId(int payrollId);

    List<Payroll> findPayrollsByEmployeeId(int employeeId);

	List<Audit> getPayrollAuditLogs();
}
