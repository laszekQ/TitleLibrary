package com.github.laszekq.titlelibrary.repositories;

import com.github.laszekq.titlelibrary.entities.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    Optional<Type> findByName(String name);
}
