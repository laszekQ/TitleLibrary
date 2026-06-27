package com.github.laszekq.titlelibrary.repositories;

import com.github.laszekq.titlelibrary.entities.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    Optional<Status> findByName(String name);
}
