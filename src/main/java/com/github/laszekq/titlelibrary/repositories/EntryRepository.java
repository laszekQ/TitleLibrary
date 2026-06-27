package com.github.laszekq.titlelibrary.repositories;

import com.github.laszekq.titlelibrary.entities.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EntryRepository extends CrudRepository<Entry, Integer> {
    Optional<Entry> getEntryById(Integer id);
}
