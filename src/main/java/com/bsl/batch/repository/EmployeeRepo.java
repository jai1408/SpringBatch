package com.bsl.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bsl.batch.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
