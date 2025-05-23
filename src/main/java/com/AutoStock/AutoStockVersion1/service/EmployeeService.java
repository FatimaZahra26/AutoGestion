package com.AutoStock.AutoStockVersion1.service;

import com.AutoStock.AutoStockVersion1.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();
    public List<Employee>getStocksEmployees();
    void saveEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee updatedEmployee);
    public Long countEmployees() throws SQLException;
    public Long countStocksEmployees()throws SQLException;
    void deleteEmployee(Long id);
}
