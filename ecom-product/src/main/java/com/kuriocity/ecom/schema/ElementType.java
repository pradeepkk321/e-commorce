package com.kuriocity.ecom.schema;

import java.util.Arrays;

public enum ElementType {

	STRING("string"),

	INT("int"),

	DOUBLE("double"),

	BOOLEAN("boolean");

	String text;

	private ElementType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static ElementType fromText(String value) {
		return Arrays.stream(values()).filter(eType -> eType.text.equalsIgnoreCase(value)).findFirst()
				.orElseThrow(() -> new RuntimeException("No valid ElementType found for '" + value + "'"));
	}
}
