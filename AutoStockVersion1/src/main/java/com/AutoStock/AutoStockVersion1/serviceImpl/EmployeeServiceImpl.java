package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.model.Employee;
import com.AutoStock.AutoStockVersion1.service.EmployeeService;
import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    Connection connection;

    public EmployeeServiceImpl() throws SQLException {
        connection = DBUtil.getConnection(); // Assume DBUtil class manages database connection
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void saveEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement;
            if (employee.getId() != null) {
                // Update existing employee
                preparedStatement = connection.prepareStatement(
                        "UPDATE employee SET name=?, email=?, phone=?, address=? WHERE id=?"
                );
                preparedStatement.setLong(5, employee.getId());
            } else {
                // Insert new employee
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO employee (name, email, phone, address) VALUES (?, ?, ?, ?)"
                );
            }
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPhone());
            preparedStatement.setString(4, employee.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE employee SET name=?, email=?, phone=?, address=? WHERE id=?"
            );
            preparedStatement.setString(1, updatedEmployee.getName());
            preparedStatement.setString(2, updatedEmployee.getEmail());
            preparedStatement.setString(3, updatedEmployee.getPhone());
            preparedStatement.setString(4, updatedEmployee.getAddress());
            preparedStatement.setLong(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return updatedEmployee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Handle errors or return appropriate response
    }

    @Override
    public void deleteEmployee(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



