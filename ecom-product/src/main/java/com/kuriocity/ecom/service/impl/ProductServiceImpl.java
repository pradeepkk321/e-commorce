package com.kuriocity.ecom.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuriocity.ecom.repo.MongoTemplateWrapper;
import com.kuriocity.ecom.schema.DynamicObject;
import com.kuriocity.ecom.schema.validator.SchemaValidator;
import com.kuriocity.ecom.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	SchemaValidator schemaValidator;
	
	@Autowired
	MongoTemplateWrapper mongoWrapper;

	@Override
	public DynamicObject addProduct(DynamicObject product) {
		
		if(schemaValidator.validateObject(product)) {
			return mongoWrapper.saveDocumentToCollection(product, product.getSchema());
		}
		
		return null;
	}

	@Override
	public DynamicObject getProductById(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DynamicObject updateProduct(Map<String, Object> product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map deleteProduct(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

}
