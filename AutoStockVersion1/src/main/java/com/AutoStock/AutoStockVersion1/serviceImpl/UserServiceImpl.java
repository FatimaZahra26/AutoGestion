package com.AutoStock.AutoStockVersion1.serviceImpl;

import com.AutoStock.AutoStockVersion1.dbutil.DBUtil;
import com.AutoStock.AutoStockVersion1.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    private final Connection connection;

    public UserServiceImpl() throws SQLException {
        connection = DBUtil.getConnection();
    }

    @Override
    public boolean loginValidation(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isValidUser = false;

        try {
            // Query to fetch user by email and password
            String query = "SELECT * FROM UERS WHERE email = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            // Check if any matching user found
            if (resultSet.next()) {
                isValidUser = true;
            } else {
                System.out.println("Invalid email or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException as needed
        } finally {
            // Close resources in finally block
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return isValidUser;
    }
}
