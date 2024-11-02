package com.khushal.journalApp.controller;

import com.khushal.journalApp.entity.JournalEntry;
import com.khushal.journalApp.entity.User;
import com.khushal.journalApp.service.JournalService;
import com.khushal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;
    @Autowired
    UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getEntryByUserName(@PathVariable String username) {

        try {
            User user = userService.findByUserName(username);
            List<JournalEntry> entries = user.getEntries();

//            if(entries != null && !entries.isEmpty()) {
//                return new ResponseEntity<>(entries, HttpStatus.OK);
//            }

            return new ResponseEntity<>(entries, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry>  createEntry(@PathVariable String username, @RequestBody JournalEntry entry) {
        try {
            journalService.saveEntry(username, entry);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalService.getById(id);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(journalEntry, HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<?> update(
            @PathVariable String username,
            @PathVariable ObjectId id,
            @RequestBody JournalEntry myEntry) {

        if(journalService.updateEntry(id, myEntry)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteEntry(
            @PathVariable ObjectId id,
            @PathVariable String username) {

        try {
            journalService.deleteEntry(id, username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
