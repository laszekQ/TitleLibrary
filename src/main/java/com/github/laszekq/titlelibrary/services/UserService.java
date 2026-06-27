package com.github.laszekq.titlelibrary.services;

import com.github.laszekq.titlelibrary.dtos.UserDTO;
import com.github.laszekq.titlelibrary.entities.Entry;
import com.github.laszekq.titlelibrary.entities.Role;
import com.github.laszekq.titlelibrary.entities.User;
import com.github.laszekq.titlelibrary.repositories.RoleRepository;
import com.github.laszekq.titlelibrary.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean tryLogin(String login, String password) {
        // TODO: implement
        return false;
    }

    public void register(UserDTO user) {
        if (userRepository.existsByLogin(user.getLogin()))
            throw new IllegalStateException("Such login already exists");

        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setLogin(user.getLogin());

        Optional<Role> role = roleRepository.findByName("User");
        if(role.isEmpty())
            throw new IllegalStateException("No User role exists");
        userEntity.setRole(role.get());
        userRepository.save(userEntity);
    }

    public List<Entry> getUserEntries(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return null;
        return user.get().getEntries();
    }
}
