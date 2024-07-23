package com.AutoStock.AutoStockVersion1.controller;

import com.AutoStock.AutoStockVersion1.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("user/{email}/{password}")
    public int userLogin(@PathVariable("email") String email, @PathVariable("password") String password) throws SQLException {
        LOGGER.info("Received login request with email: " + email + " and password: " + password);
        boolean isValidLogin = userService.loginValidation(email, password);

        // Convert boolean result to int
        int flag = isValidLogin ? 1 : 0;

        LOGGER.info("Login result: " + flag);
        return flag;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "You have been logged out successfully";
    }
}
