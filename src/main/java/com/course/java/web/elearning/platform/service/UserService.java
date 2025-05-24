package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.UserDto;
import com.course.java.web.elearning.platform.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto user);
    User updateUser(User user);
    void deleteUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUserDetails(Long id, String detail, Object value);
    void save(User user);
}
