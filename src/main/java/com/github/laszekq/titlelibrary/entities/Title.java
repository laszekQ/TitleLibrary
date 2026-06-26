package com.github.laszekq.titlelibrary.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

import java.util.List;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private List<Name> names;

    @Nonnull
    private String description;

    @Nonnull
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToMany(mappedBy = "title",  fetch = FetchType.LAZY)
    private List<Entry> entries;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NonNull String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public @NonNull Integer getYear() {
        return year;
    }

    public void setYear(@NonNull Integer year) {
        this.year = year;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
