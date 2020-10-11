package com.skyforce.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.skyforce.application.models.Consumer;

@Repository
public interface ConsumerRepository extends MongoRepository<Consumer,String>{

}
