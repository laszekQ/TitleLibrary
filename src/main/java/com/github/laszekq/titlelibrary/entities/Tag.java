package com.github.laszekq.titlelibrary.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Title> titles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }
}
