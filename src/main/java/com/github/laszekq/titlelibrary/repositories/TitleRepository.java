package com.github.laszekq.titlelibrary.repositories;

import com.github.laszekq.titlelibrary.entities.Title;
import com.github.laszekq.titlelibrary.entities.Type;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title, Long> {
    List<Title> findAllByType(Type type);

    @Query( nativeQuery = true, value =
            "SELECT t.id, t.year, t.type_id " +
            "FROM TITLE t " +
            "JOIN NAME n " +
            "ON n.title_id = t.id WHERE ts @@ to_tsquery('simple', :name)")
    List<Title> findAllByNameLike(@Param(":name") String name);
}
