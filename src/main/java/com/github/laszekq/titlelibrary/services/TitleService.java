package com.github.laszekq.titlelibrary.services;

import com.github.laszekq.titlelibrary.entities.Title;
import com.github.laszekq.titlelibrary.entities.Type;
import com.github.laszekq.titlelibrary.repositories.TitleRepository;
import com.github.laszekq.titlelibrary.repositories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TitleService {
    private final TitleRepository titleRepository;
    private final TypeRepository typeRepository;

    public TitleService(TitleRepository titleRepository, TypeRepository typeRepository) {
        this.titleRepository = titleRepository;
        this.typeRepository = typeRepository;
    }

    public List<Title> getAll() {
        return (List<Title>) titleRepository.findAll();
    }

    public List<Title> getByType(String type) {
        Optional<Type> type_t = typeRepository.findByName(type);
        if (type_t.isEmpty())
            return Collections.emptyList();
        return type_t.get().getTitles();
    }

    public List<Title> getByName(String name) {
        return titleRepository.findAllByNameLike(name);
    }
}
