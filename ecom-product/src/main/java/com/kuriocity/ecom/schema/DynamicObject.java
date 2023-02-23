package com.kuriocity.ecom.schema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class DynamicObject extends LinkedHashMap<String, Object> implements Cloneable {

	public static final String ID = "_id";

	public static final String SCHEMA = "_schema";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1043974528168763242L;

	public DynamicObject() {
		super.put(ID, UUID.randomUUID().toString().replace("-", ""));
	}
	
	public DynamicObject(Map<String, Object> map) {
		super(map);
		super.put(ID, UUID.randomUUID().toString().replace("-", ""));
	}

	public String getSchema() {
		return this.get(SCHEMA).toString();
	}
	
	public void setSchema(String schema) {
		this.put(SCHEMA, schema);
	}

	public String getId() {
		return this.get(ID).toString();
	}

	@Override
	public DynamicObject clone() {
		return (DynamicObject) super.clone();
	}

}
