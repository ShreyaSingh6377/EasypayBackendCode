package com.hexaware.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.DTO.PayrollDTO;
import com.hexaware.Entity.Audit;
import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Payroll;
import com.hexaware.Entity.Users;
import com.hexaware.Repository.AuditRepository;
import com.hexaware.Repository.EmployeeRepo;
import com.hexaware.Repository.PayrollRepo;
import com.hexaware.ServiceInterface.PayrollInterface;

@Service
public class PayrollService implements PayrollInterface {

    @Autowired
    private PayrollRepo payrollRepo;

    @Autowired
    private EmployeeRepo employeeRepo;
    
    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private UserService userService;  // Service to get the current logged-in user

    private static final double HR_DEPARTMENT_RATE = 50.0;
    private static final double IT_DEPARTMENT_RATE = 70.0;
    private static final double SALES_DEPARTMENT_RATE = 60.0;
    private static final double DEVELOPER_DEPARTMENT_RATE = 80.0;
    private static final double FINANCE_DEPARTMENT_RATE = 65.0;

    @Override
    public void removePayroll(int payrollId) {
        Payroll payroll = payrollRepo.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));
        
        payrollRepo.delete(payroll);

        // Log the audit event
        logAudit("Deleted payroll for Employee ID: " + payroll.getEmployee().getEmpId());
    }

    @Override
    public Payroll updatePayroll(int payrollId, PayrollDTO updatedPayrollDTO) {
        Payroll existingPayroll = payrollRepo.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        Employee employee = employeeRepo.findById(updatedPayrollDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingPayroll.setEmployee(employee);
        existingPayroll.setPayDate(existingPayroll.getPayDate());
        existingPayroll.setBasicSalary(updatedPayrollDTO.getBasicSalary());
        existingPayroll.setBonuses(updatedPayrollDTO.getBonuses());
        existingPayroll.setDeductions(updatedPayrollDTO.getDeductions());
        existingPayroll.setNetSalary(updatedPayrollDTO.getNetSalary());
        existingPayroll.setHoursWorked(updatedPayrollDTO.getHoursWorked());

        Payroll updatedPayroll = payrollRepo.save(existingPayroll);

        // Log the audit event
        logAudit("Updated payroll for Employee ID: " + updatedPayroll.getEmployee().getEmpId());

        return updatedPayroll;
    }

    @Override
    public Payroll calculatePayroll(PayrollDTO payrollDTO) {
        Employee employee = employeeRepo.findById(payrollDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        double hourlyRate = getHourlyRate(employee);
        double basicSalary = payrollDTO.getHoursWorked() * hourlyRate;

        double bonuses = calculateBonuses(basicSalary);
        double deductions = calculateDeductions(basicSalary);

        double netSalary = calculateNetSalary(basicSalary, bonuses, deductions);

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setPayDate(payrollDTO.getPayDate());
        payroll.setBasicSalary(basicSalary);
        payroll.setBonuses(bonuses);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);
        payroll.setHoursWorked(payrollDTO.getHoursWorked());

        Payroll savedPayroll = payrollRepo.save(payroll);

        // Log the audit event
        logAudit("Calculated payroll for Employee ID: " + savedPayroll.getEmployee().getEmpId());

        return savedPayroll;
    }

    @Override
    public Payroll findByPayrollId(int payrollId) {
        return payrollRepo.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));
    }

    @Override
    public List<Payroll> findPayrollsByEmployeeId(int employeeId) {
        return payrollRepo.findByEmployee_EmpId(employeeId);
    }
    
    private double calculateNetSalary(double basicSalary, double bonuses, double deductions) {
        return basicSalary + bonuses - deductions;
    }

    private double getHourlyRate(Employee employee) {
        String deptName = employee.getDepartment().getDeptName();
        switch (deptName) {
            case "HR":
                return HR_DEPARTMENT_RATE;
            case "IT":
                return IT_DEPARTMENT_RATE;
            case "Sales":
                return SALES_DEPARTMENT_RATE;
            case "Developer":
                return DEVELOPER_DEPARTMENT_RATE;
            case "Finance":
                return FINANCE_DEPARTMENT_RATE;
            default:
                return 40.0;
        }
    }

    private double calculateBonuses(double basicSalary) {
        return basicSalary * 0.10;
    }

    private double calculateDeductions(double basicSalary) {
        return basicSalary * 0.05;
    }

    public List<Audit> getPayrollAuditLogs() {
        return auditRepository.findByActionContaining("payroll");
    }

    // Helper method to log audit events
    private void logAudit(String actionDescription) {
        // Get the current logged-in user (e.g., payroll manager)
        Users currentUser = userService.getCurrentLoggedInUser();

        // Create an Audit log entry
        Audit auditLog = new Audit(actionDescription, currentUser, new java.sql.Timestamp(System.currentTimeMillis()));

        // Save the audit log
        auditRepository.save(auditLog);
    }
}
