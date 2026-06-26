package com.github.laszekq.titlelibrary.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Title> titles;


    public Long getId() {
        return id;
    }

    public void setId(@Nonnull Long id) {
        this.id = id;
    }

    @Nonnull
    public String getName() {
        return name;
    }
    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public List<Title> getTitles() {
        return titles;
    }
    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
}
