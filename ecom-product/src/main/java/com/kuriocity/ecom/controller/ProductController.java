package com.kuriocity.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuriocity.ecom.schema.DynamicObject;
import com.kuriocity.ecom.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired	
	ProductService productService;

	@PostMapping
	public DynamicObject addNewProdcut(@RequestBody DynamicObject product) {
		
		return productService.addProduct(product);
		
	}
}
