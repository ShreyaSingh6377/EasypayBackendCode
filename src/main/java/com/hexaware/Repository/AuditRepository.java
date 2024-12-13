package com.hexaware.Repository;

import com.hexaware.Entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
    
    // Method to find all audit logs related to payroll actions
    List<Audit> findByActionContaining(String actionKeyword);
}
