package com.github.laszekq.titlelibrary.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private String name;
    @Nonnull
    private String login;
    @Nonnull
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Entry> entries = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setLogin(@Nonnull String login) {
        this.login = login;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setPassword(@Nonnull String password) {
        this.password = password;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
