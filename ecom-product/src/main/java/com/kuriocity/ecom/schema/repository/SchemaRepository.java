package com.kuriocity.ecom.schema.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kuriocity.ecom.schema.Schema;

@Repository
public interface SchemaRepository extends MongoRepository<Schema, String> {

}
