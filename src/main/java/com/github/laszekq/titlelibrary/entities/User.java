package com.github.laszekq.titlelibrary.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nonnull
    private String name;
    @Nonnull
    private String login;
    @Nonnull
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Entry> entries = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
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
