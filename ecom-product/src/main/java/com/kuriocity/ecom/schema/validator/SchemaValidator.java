package com.kuriocity.ecom.schema.validator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kuriocity.ecom.schema.DynamicObject;
import com.kuriocity.ecom.schema.Schema;
import com.kuriocity.ecom.schema.SchemaElement;
import com.kuriocity.ecom.schema.exception.SchemaMisMatchException;
import com.kuriocity.ecom.schema.repository.SchemaRepository;

import io.micrometer.common.util.StringUtils;

@Component
public class SchemaValidator {

	@Autowired
	SchemaRepository schemaRepository;

	public boolean validateObject(DynamicObject object) {

		if (!object.containsKey(DynamicObject.SCHEMA))
			throw new SchemaMisMatchException("'" + DynamicObject.SCHEMA + "' is mandatory and it is not present");

		String schema = object.get(DynamicObject.SCHEMA).toString();

		Optional<Schema> findById = schemaRepository.findById(schema);

		if (findById.isPresent()) {
			Schema productSchema = findById.get();

			List<SchemaElement> elements = productSchema.getElements();

			validateForInvalidElements(object, elements);

			for (SchemaElement schemaElement : elements) {
				validateRequired(object, schemaElement);
				validateDataType(object, schemaElement);
				validateRegex(object, schemaElement);

			}

			arrangeOrder(object, elements);

		} else {
			throw new SchemaMisMatchException("Unknown schema '" + schema + "'");
		}

		return true;
	}

	private void validateForInvalidElements(DynamicObject object, List<SchemaElement> elements) {

		List<String> schemaFields = elements.stream().map(element -> element.getName()).collect(Collectors.toList());
		schemaFields.add(DynamicObject.SCHEMA);
		schemaFields.add(DynamicObject.ID);

		List<String> dataFields = new ArrayList<String>(object.keySet());

		dataFields.removeAll(schemaFields);
		if (!dataFields.isEmpty())
			throw new SchemaMisMatchException(
					"Fields " + dataFields + " not defined in schema '" + object.getSchema() + "'");

	}

	private void validateRequired(DynamicObject object, SchemaElement element) {
		if (element.isRequired()) {
			if (!object.containsKey(element.getName())) {
				throw new SchemaMisMatchException(
						"'" + element.getName() + "' is mandatory and it is not present in the object");
			} else if (StringUtils.isBlank(object.get(element.getName()).toString())) {
				throw new SchemaMisMatchException(
						"'" + element.getName() + "' is mandatory and it is either null or blank in the object");
			}
		}

	}

	private void validateDataType(DynamicObject productRequest, SchemaElement schemaElement) {

		if (productRequest.containsKey(schemaElement.getName())) {

			Object value = productRequest.get(schemaElement.getName());

			switch (schemaElement.getType()) {
			case STRING:
				if (value instanceof String)
					break;
				else
					throw new SchemaMisMatchException(
							schemaElement.getName() + " should be of type " + schemaElement.getType());

			case DOUBLE:
				if (value instanceof Double || value instanceof Float)
					break;
				else
					throw new SchemaMisMatchException(
							schemaElement.getName() + " should be of type " + schemaElement.getType());

			case INT:
				if (value instanceof Integer || value instanceof Short || value instanceof Byte
						|| value instanceof Long)
					break;
				else
					throw new SchemaMisMatchException(
							schemaElement.getName() + " should be of type " + schemaElement.getType());

			case BOOLEAN:
				if (value instanceof Boolean)
					break;
				else
					throw new SchemaMisMatchException(
							schemaElement.getName() + " should be of type " + schemaElement.getType());

			default:
				throw new SchemaMisMatchException(
						schemaElement.getName() + " should be of type " + schemaElement.getType());
			}
		}

	}

	private void validateRegex(DynamicObject productRequest, SchemaElement schemaElement) {
		// TODO Auto-generated method stub

	}

	private void arrangeOrder(DynamicObject object, List<SchemaElement> elements) {

		DynamicObject clone = object.clone();

		List<String> keyList = new ArrayList<>(clone.keySet());
		List<String> schemaOrder = elements.stream().map(element -> element.getName()).collect(Collectors.toList());
		keyList.sort(Comparator.comparingInt(schemaOrder::indexOf));

		object.clear();
		for (String key : keyList) {
			object.put(key, clone.get(key));
		}
	}
}
