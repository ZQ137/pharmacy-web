package com.example.pharmacy.service;

import com.example.pharmacy.entity.User;
import com.example.pharmacy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Optional<User> getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public User registerUser(User user) {
        userMapper.insertUser(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }
}
