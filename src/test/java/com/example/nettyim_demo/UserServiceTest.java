package com.example.nettyim_demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.nettyim_demo.service.UserService;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    private UserService userService;

    @Test
    public void testRegister() {
        boolean result = userService.register("testuser", "password123");
        assertTrue(result, "User registration should be successful");
    }

    @Test
    public void testLogin() {
        userService.register("testuser", "password123");
        var user = userService.login("testuser", "password123");
        assertTrue(user != null && user.getUsername().equals("testuser"), "User login should be successful");
    }

    @Test
    public void testUpdateUser() {
        var user = userService.findByUsername("testuser");
        user.setStatus(0);
        boolean result = userService.updateUser(user);
        assertTrue(result, "User update should be successful");
    }

    @Test
    public void testDeleteUser() {
        var user = userService.findByUsername("testuser");
        boolean result = userService.deleteUser(user.getUid());
        assertTrue(result, "User deletion should be successful");
    }

}
