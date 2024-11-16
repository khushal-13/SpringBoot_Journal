package com.khushal.journalApp.service;

import com.khushal.journalApp.entity.ConfigureJournalAppEntity;
import com.khushal.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AppCache {

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {

        List<ConfigureJournalAppEntity> all  = configJournalAppRepository.findAll();
        for (ConfigureJournalAppEntity entity : all) {
            APP_CACHE.put(entity.getKey(), entity.getValue());
        }

    }
    
}
