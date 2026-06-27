package com.github.laszekq.titlelibrary.dtos;

import com.github.laszekq.titlelibrary.entities.Role;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UserDTO {
    @NotBlank
    @Length(max = 32)
    private String name;

    @NotBlank
    @Length(min = 8, max = 32)
    private String login;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;

    @NotBlank
    private Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
