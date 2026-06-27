package com.github.laszekq.titlelibrary.services;

import com.github.laszekq.titlelibrary.dtos.UserDTO;
import com.github.laszekq.titlelibrary.entities.Entry;
import com.github.laszekq.titlelibrary.entities.User;
import com.github.laszekq.titlelibrary.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public boolean tryLogin(String login, String password) {
        // TODO: implement
        return false;
    }

    public boolean tryRegister(UserDTO user) {
        // TODO: implement
        return false;
    }

    public List<Entry> getUserEntries(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return null;
        return user.get().getEntries();
    }
}
