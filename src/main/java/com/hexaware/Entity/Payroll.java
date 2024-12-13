package com.hexaware.Entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_id")
    private int payrollId;

    @ManyToOne(cascade = { CascadeType.PERSIST,CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "pay_date", nullable = false)
    private Date payDate;

    @Column(name = "basic_salary", nullable = false)
    private double basicSalary;

    @Column(name = "bonuses", nullable = false)
    private double bonuses = 0;

    @Column(name = "deductions", nullable = false)
    private double deductions = 0;

    @Column(name = "net_salary", nullable = false)
    private double netSalary;

    @Column(name = "hours_worked", nullable = false)
    private double hoursWorked;

    // Constructors
    public Payroll() {
    }

    public Payroll(Employee employee, java.sql.Date payDate, double basicSalary, double bonuses, double deductions, double netSalary, double hoursWorked) {
        this.employee = employee;
        this.payDate = payDate;
        this.basicSalary = basicSalary;
        this.bonuses = bonuses;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.hoursWorked = hoursWorked;
    }

    // Getters and Setters
    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date date) {
        this.payDate = date;
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

    // toString Method (Optional)
    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", employee=" + employee.getFirstName() + " " + employee.getLastName() +
                ", payDate=" + payDate +
                ", basicSalary=" + basicSalary +
                ", bonuses=" + bonuses +
                ", deductions=" + deductions +
                ", netSalary=" + netSalary +
                ", hoursWorked=" + hoursWorked +
                '}';
    }
}