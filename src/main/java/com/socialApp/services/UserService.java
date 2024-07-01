package com.socialApp.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.socialApp.entities.User;
import com.socialApp.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(UUID userId, User userDetails) {
        return userRepository.findById(userId).map(user -> {
            user.setName(userDetails.getName());
            user.setMobile(userDetails.getMobile());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        });
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByName(String name) {
        return userRepository.findAll().stream()
            .filter(user -> user.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}