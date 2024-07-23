package com.AutoStock.AutoStockVersion1.service;

import java.sql.SQLException;

public interface UserService {
    boolean loginValidation(String email, String password) throws SQLException;
}
