package com.kuriocity.ecom.schema;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("schema")
public class Schema {
	
	@Id
	private String type;

	private List<SchemaElement> elements;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SchemaElement> getElements() {
		return elements;
	}

	public void setElements(List<SchemaElement> elements) {
		this.elements = elements;
	}

}
