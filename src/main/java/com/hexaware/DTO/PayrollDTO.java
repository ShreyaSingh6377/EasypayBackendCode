package com.hexaware.DTO;

import java.util.Date;

public class PayrollDTO {

    private int payrollId;
    private int employeeId;
    private Date payDate;
    private double basicSalary;
    private double bonuses = 0;
    private double deductions = 0;
    private double netSalary;
    private double hoursWorked;

    public PayrollDTO() {
        
    }

    public PayrollDTO(int payrollId, int employeeId, Date payDate, double basicSalary, double bonuses,
                      double deductions, double netSalary, double hoursWorked) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.payDate = payDate;
        this.basicSalary = basicSalary;
        this.bonuses = bonuses;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.hoursWorked = hoursWorked;
    }

    // Getters and setters
    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getBonuses() {
        return bonuses;
    }

    public void setBonuses(double bonuses) {
        this.bonuses = bonuses;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String toString() {
        return "PayrollDTO{" +
                "payrollId=" + payrollId +
                ", employeeId=" + employeeId +
                ", payDate=" + payDate +
                ", basicSalary=" + basicSalary +
                ", bonuses=" + bonuses +
                ", deductions=" + deductions +
                ", netSalary=" + netSalary +
                ", hoursWorked=" + hoursWorked +
                '}';
    }
}
