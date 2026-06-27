package com.github.laszekq.titlelibrary.services;

import com.github.laszekq.titlelibrary.dtos.EntryDTO;
import com.github.laszekq.titlelibrary.entities.Entry;
import com.github.laszekq.titlelibrary.entities.Status;
import com.github.laszekq.titlelibrary.entities.User;
import com.github.laszekq.titlelibrary.repositories.EntryRepository;
import com.github.laszekq.titlelibrary.repositories.StatusRepository;
import com.github.laszekq.titlelibrary.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService {
    private final UserRepository userRepository;
    private final EntryRepository entryRepository;
    private final StatusRepository statusRepository;

    public EntryService(UserRepository userRepository,
                        EntryRepository entryRepository,
                        StatusRepository statusRepository) {
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
        this.statusRepository = statusRepository;
    }

    public List<Entry> getAllFromLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty())
            return Collections.emptyList();
        return user.get().getEntries();
    }

    public void updateEntry(EntryDTO dto) {
        Optional<Entry> entry = entryRepository.findById(dto.getId());
        if (entry.isEmpty())
            throw new IllegalArgumentException("No such entry");

        Optional<Status> status = statusRepository.findByName(dto.getStatus());
        if (status.isEmpty())
            throw new IllegalArgumentException("No such status");

        Entry e = entry.get();
        e.setRating(dto.getRating());
        e.setDate(LocalDate.parse(dto.getDate()));
        e.setStatus(status.get());
        entryRepository.save(e);
    }

    @Transactional
    public void removeEntry(Integer id) {
        Optional<Entry> entry = entryRepository.findById(id);
        if (entry.isEmpty())
            throw new IllegalArgumentException("No such entry");
        entryRepository.delete(entry.get());
    }
}
