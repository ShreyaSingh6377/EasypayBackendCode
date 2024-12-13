package com.hexaware.Repository;

import com.hexaware.Entity.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
	
	@Query("SELECT u FROM Users u WHERE u.username = :username")
	Users findByUsername(@Param("username") String username);
	
	@Query(value = "SELECT COUNT(*) FROM users WHERE role = 'EMPLOYEE'", nativeQuery = true)
    long countTotalEmployees();
	
	@Query(value = "SELECT COUNT(*) FROM users WHERE role = 'PAYROLL_MANAGER'", nativeQuery = true)
    long countTotalPayrollManager();
	
	@Query(value = "SELECT COUNT(*) FROM users WHERE role = 'ADMIN'", nativeQuery = true)
    long countTotalAdmin();

}