package com.khushal.journalApp.controller;

import com.khushal.journalApp.entity.JournalEntry;
import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.service.JournalService;
import com.khushal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;
    @Autowired
    UserService userService;


    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getEntryByUserName() {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.findByUserName(username);
            List<JournalEntry> entries = user.getEntries();

            return new ResponseEntity<>(entries, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry entry) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalService.saveEntry(username, entry);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);
        List<JournalEntry> entries = user.getEntries().stream().filter(x -> x.getId().equals(id)).toList();

        if(!entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry myEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);
        List<JournalEntry> entries = user.getEntries().stream().filter(x -> x.getId().equals(id)).toList();

        if(!entries.isEmpty()) {
            JournalEntry updated = journalService.updateEntry(id, myEntry);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntry(
            @PathVariable ObjectId id) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalService.deleteEntry(id, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
