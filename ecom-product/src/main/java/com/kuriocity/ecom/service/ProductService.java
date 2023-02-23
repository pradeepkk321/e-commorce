package com.kuriocity.ecom.service;

import java.util.Map;

import com.kuriocity.ecom.schema.DynamicObject;

public interface ProductService {

	public DynamicObject addProduct(DynamicObject product);
	
	public DynamicObject getProductById(String productId);
	
	Map updateProduct(Map<String, Object> product);
	
	Map deleteProduct(String productId);
}
