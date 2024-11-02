package com.khushal.journalApp.service;

import com.khushal.journalApp.entity.JournalEntry;
import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalService {

    @Autowired
    JournalRepository journalRepository;
    @Autowired
    UserService userService;


    @Transactional  //Atomicity : Execute all or none
    public void saveEntry(String username, JournalEntry journalEntry) {

        try {
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepository.save(journalEntry);

            User user = userService.findByUserName(username);
            user.getEntries().add(saved);
//            user.setUserName(null);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        }

    }

    public List<JournalEntry> getAllEntries() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalRepository.findById(id);
    }

    public void deleteEntry(ObjectId id, String username) {
        User user = userService.findByUserName(username);
        user.getEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalRepository.deleteById(id);
    }

    public boolean updateEntry(ObjectId id, JournalEntry myEntry) {


        JournalEntry old = journalRepository.findById(id).orElse(null);

        if(old!=null) {

            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().isEmpty() ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null && !myEntry.getContent().isEmpty() ? myEntry.getContent() : old.getContent());
            journalRepository.save(old);
            return true;  // Updated
        }

        return false;     // Not Updated
    }
}
