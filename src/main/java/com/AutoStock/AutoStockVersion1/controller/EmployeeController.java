package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.model.Employee;
import com.AutoStock.AutoStockVersion1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
@GetMapping("/employeeStocks")
public List<Employee>getStocksEmployees(){
        return employeeService.getStocksEmployees();
}
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }


    @GetMapping("/count")
    public Long  countEmployees() throws SQLException {
        return employeeService.countEmployees();
    }
    @GetMapping("/countStocks")
    public Long  countStocksEmployees() throws SQLException {
        return employeeService.countStocksEmployees();
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        newEmployee.setSecteur("parc-auto");

        employeeService.saveEmployee(newEmployee);

        return newEmployee;
    }
}

