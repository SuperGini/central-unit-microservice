package com.gini.repositories;

import com.gini.entities.PartDetails;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PartDetailsRepository extends MongoRepository<PartDetails, String> {
}
