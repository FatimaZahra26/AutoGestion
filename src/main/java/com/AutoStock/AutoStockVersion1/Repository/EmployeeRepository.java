package com.AutoStock.AutoStockVersion1.Repository;


import com.AutoStock.AutoStockVersion1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
