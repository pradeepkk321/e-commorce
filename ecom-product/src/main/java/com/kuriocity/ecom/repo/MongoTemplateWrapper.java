package com.kuriocity.ecom.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.kuriocity.ecom.schema.DynamicObject;

@Repository
public class MongoTemplateWrapper {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public DynamicObject saveDocumentToCollection(DynamicObject document, String collectionName) {
		
		document.put(DynamicObject.ID, document.getId());
		
		return mongoTemplate.insert(document, collectionName);
	}
}
