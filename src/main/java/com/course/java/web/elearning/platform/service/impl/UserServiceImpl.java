package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.UserDto;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.exception.DuplicateEmailException;
import com.course.java.web.elearning.platform.exception.DuplicateUsernameException;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.UserRepository;
import com.course.java.web.elearning.platform.security.Role;
import com.course.java.web.elearning.platform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new DuplicateUsernameException(userDto.getUsername());
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateEmailException(userDto.getEmail());
        }

        final User userForCreate = buildUser(userDto);
        return userRepository.save(userForCreate);
    }

    @Override
    public User updateUser(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID = '%d' not found", user.getId())));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.valueOf(id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User buildUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        //user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.STUDENT.getDescription()));
        } else {
            user.setRoles(userDto.getRoles());
        }

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        return user;
    }

    @Override
    public User updateUserDetails(Long id, String detail, Object value) {
        User existingUser = getUserById(id);
        switch (detail) {
            case "username":
                existingUser.setUsername((String) value);
                break;
            case "email":
                existingUser.setEmail((String) value);
                break;
            case "name":
                String name = (String) value;
                String[] names = name.split(" ");
                if (names.length != 2) {
                    throw new IllegalArgumentException("Invalid full name: " + value);
                }
                existingUser.setFirstName(names[0]);
                existingUser.setLastName(names[1]);
                break;
            case "role":
                existingUser.setRoles(new HashSet<>(List.of((String) value)));
                break;
            default:
                throw new IllegalArgumentException("Invalid user detail: " + detail);
        }
        save(existingUser);
        return existingUser;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
