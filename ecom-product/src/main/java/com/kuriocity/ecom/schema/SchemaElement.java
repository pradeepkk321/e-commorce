package com.kuriocity.ecom.schema;

public class SchemaElement {

	private String name;

	private ElementType type;

	private boolean required;

	private String regex;

	public String getName() {
		return name;
	}

	public SchemaElement(String name, ElementType type, boolean required, String regex) {
		super();
		this.name = name;
		this.type = type;
		this.required = required;
		this.regex = regex;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ElementType getType() {
		return type;
	}

	public void setType(ElementType type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
