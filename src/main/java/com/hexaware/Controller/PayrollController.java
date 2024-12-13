package com.hexaware.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.DTO.PayrollDTO;
import com.hexaware.Entity.Audit;
import com.hexaware.Entity.Payroll;
import com.hexaware.ServiceInterface.PayrollInterface;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:3000")
public class PayrollController {

    @Autowired
    private PayrollInterface payrollService;

    // Remove Payroll
    @DeleteMapping("/payroll/remove_payroll/{payrollId}")
    public ResponseEntity<String> removePayroll(@PathVariable int payrollId) {
        payrollService.removePayroll(payrollId);
        return ResponseEntity.ok("Payroll record with ID " + payrollId + " has been deleted successfully.");
    }

    // Update Payroll
    @PutMapping("/payroll/update_payroll/{payrollId}")
    public ResponseEntity<Payroll> updatePayroll(
            @PathVariable int payrollId, 
            @RequestBody PayrollDTO updatedPayrollDTO) {
        Payroll updatedPayroll = payrollService.updatePayroll(payrollId, updatedPayrollDTO);
        return new ResponseEntity<>(updatedPayroll, HttpStatus.OK);
    }

    // Calculate Payroll
    @PostMapping("/payroll/calculate_payroll")
    public ResponseEntity<Payroll> calculatePayroll(@RequestBody PayrollDTO payrollDTO) {
        Payroll calculatedPayroll = payrollService.calculatePayroll(payrollDTO);
        return new ResponseEntity<>(calculatedPayroll, HttpStatus.OK);
    }

    // Find Payroll by ID
    @GetMapping("/payroll/get_payrollById/{payrollId}")
    public ResponseEntity<Payroll> findPayrollById(@PathVariable int payrollId) {
        Payroll payroll = payrollService.findByPayrollId(payrollId);
        return new ResponseEntity<>(payroll, HttpStatus.OK);
    }

    // Find Payrolls by Employee ID
    @GetMapping("/payroll/get_payrollByEmployeeId/{employeeId}")
    public ResponseEntity<List<Payroll>> findPayrollsByEmployeeId(@PathVariable int employeeId) {
        List<Payroll> payrolls = payrollService.findPayrollsByEmployeeId(employeeId);
        return new ResponseEntity<>(payrolls, HttpStatus.OK);
    }
    
    @GetMapping("/payroll/audit")
    public List<Audit> getPayrollAuditLogs() {
        return payrollService.getPayrollAuditLogs();
    }
}
