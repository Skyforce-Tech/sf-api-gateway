package com.skyforce.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.skyforce.application.models.Provider;

@Repository
public interface ProviderRepository extends MongoRepository<Provider,String>{
	
}
