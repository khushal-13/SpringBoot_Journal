package com.khushal.journalApp.repository;

import com.khushal.journalApp.entity.ConfigureJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJournalAppRepository extends MongoRepository<ConfigureJournalAppEntity, ObjectId> {

}
