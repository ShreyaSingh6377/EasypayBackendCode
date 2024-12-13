package com.hexaware.Repository;

import com.hexaware.Entity.Employee;
import com.hexaware.Entity.Leaves;
import com.hexaware.Entity.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    
    @Query(value = "SELECT * FROM Employee WHERE dept_id = :departmentId", nativeQuery = true)
    List<Employee> findByDepartment(@Param("departmentId") int departmentId);

    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName")
    Optional<Employee> findByFirstName(@Param("firstName") String firstName);

}

