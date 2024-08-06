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
        connection = DBUtil.getConnection();
    }

    @Override
     public List<Employee>getStocksEmployees(){
        List<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE secteur='stocks'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setCIN(rs.getString("cin"));
                employee.setSecteur(rs.getString("secteur"));
                employee.setEchelon(rs.getString("echelon"));
                employee.setPPR(rs.getString("ppr"));
                employee.setSituation_familiale(rs.getString("situation_familiale"));
                employee.setNumero_assurance_sociale(rs.getString("numero_assurance_sociale"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
     }
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE secteur='parc-auto'");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setCIN(rs.getString("cin"));
                employee.setSecteur(rs.getString("secteur"));
                employee.setEchelon(rs.getString("echelon"));
                employee.setPPR(rs.getString("ppr"));
                employee.setSituation_familiale(rs.getString("situation_familiale"));
                employee.setNumero_assurance_sociale(rs.getString("numero_assurance_sociale"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void saveEmployee(Employee employee) {
        if (!"parc-auto".equals(employee.getSecteur())) {
            throw new IllegalArgumentException("Le secteur doit être 'parc-auto'");
        }

        try {
            PreparedStatement preparedStatement;
            if (employee.getId() != null) {
                // Update existing employee
                preparedStatement = connection.prepareStatement(
                        "UPDATE employee SET name=?, email=?, phone=?, address=?, cin=?, situation_familiale=?, ppr=?, numero_assurance_sociale=?, echelon=?, secteur=? WHERE id=?"
                );
                preparedStatement.setLong(11, employee.getId());
            } else {
                // Insert new employee
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO employee (name, email, phone, address, cin, situation_familiale, ppr, numero_assurance_sociale, echelon, secteur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                );
            }
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPhone());
            preparedStatement.setString(4, employee.getAddress());
            preparedStatement.setString(5, employee.getCIN());
            preparedStatement.setString(6, employee.getSituation_familiale());
            preparedStatement.setString(7, employee.getPPR());
            preparedStatement.setString(8, employee.getNumero_assurance_sociale());
            preparedStatement.setString(9, employee.getEchelon());
            preparedStatement.setString(10, employee.getSecteur());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        if (!"parc-auto".equals(updatedEmployee.getSecteur())) {
            throw new IllegalArgumentException("Le secteur doit être 'parc-auto'");
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE employee SET name=?, email=?, phone=?, address=?, cin=?, situation_familiale=?, ppr=?, numero_assurance_sociale=?, echelon=?, secteur=? WHERE id=?"
            );
            preparedStatement.setString(1, updatedEmployee.getName());
            preparedStatement.setString(2, updatedEmployee.getEmail());
            preparedStatement.setString(3, updatedEmployee.getPhone());
            preparedStatement.setString(4, updatedEmployee.getAddress());
            preparedStatement.setString(5, updatedEmployee.getCIN());
            preparedStatement.setString(6, updatedEmployee.getSituation_familiale());
            preparedStatement.setString(7, updatedEmployee.getPPR());
            preparedStatement.setString(8, updatedEmployee.getNumero_assurance_sociale());
            preparedStatement.setString(9, updatedEmployee.getEchelon());
            preparedStatement.setString(10, updatedEmployee.getSecteur());

            preparedStatement.setLong(11, id);

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
    public Long countEmployees() throws SQLException {
        Long count = 0L;
        String query = "SELECT COUNT(*) AS total FROM employee WHERE secteur='parc-auto'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("total");
            }
        }
        return count;
    }
    @Override
    public Long countStocksEmployees() throws SQLException {
        Long count = 0L;
        String query = "SELECT COUNT(*) AS total FROM employee WHERE secteur='stocks'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getLong("total");
            }
        }
        return count;
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
