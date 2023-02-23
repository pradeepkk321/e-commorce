package com.kuriocity.ecom.schema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kuriocity.ecom.schema.Schema;
import com.kuriocity.ecom.schema.repository.SchemaRepository;

@RestController
@RequestMapping("/api/schema")
public class SchemaController {

	@Autowired
	SchemaRepository schemaRepository;

	@PostMapping
	public Schema createNewProductType(@RequestBody Schema schema) {
		return schemaRepository.save(schema);
	}

	@GetMapping("/{schemaType}")
	public Schema getSchemaByType(@PathVariable String schemaType) {
		return schemaRepository.findById(schemaType)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public List<Schema> getAllSchema() {
		return schemaRepository.findAll();
	}
}
