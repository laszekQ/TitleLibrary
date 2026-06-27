package com.github.laszekq.titlelibrary;

import com.github.laszekq.titlelibrary.dtos.UserDTO;
import com.github.laszekq.titlelibrary.entities.Role;
import com.github.laszekq.titlelibrary.repositories.RoleRepository;
import com.github.laszekq.titlelibrary.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testService() {
        assertNotNull(userService);
    }

    @Test
    public void testRegister() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("user");
        userDTO.setLogin("abcdefgh");
        userDTO.setPassword("abcdefgh");

        Optional<Role> role = roleRepository.findByName("User");
        assert(role.isPresent());
        userDTO.setRole(roleRepository.findByName("User").get());

        userService.register(userDTO);
    }

    @Test
    @Transactional
    public void testDelete() {
        userService.removeUser("abcdefgh");
    }
}
