package com.github.laszekq.titlelibrary.repositories;

import com.github.laszekq.titlelibrary.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Integer id);
    boolean existsByLogin(String login);
}
