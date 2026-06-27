package com.github.laszekq.titlelibrary.repositories;

import com.github.laszekq.titlelibrary.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
