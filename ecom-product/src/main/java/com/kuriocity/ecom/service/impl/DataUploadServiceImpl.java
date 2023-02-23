package com.kuriocity.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kuriocity.ecom.schema.DynamicObject;
import com.kuriocity.ecom.schema.ElementType;
import com.kuriocity.ecom.schema.Schema;
import com.kuriocity.ecom.schema.SchemaElement;
import com.kuriocity.ecom.schema.repository.SchemaRepository;
import com.kuriocity.ecom.service.DataUploadService;
import com.kuriocity.ecom.service.ProductService;

@Service
public class DataUploadServiceImpl implements DataUploadService {
	
	@Autowired
	SchemaRepository schemaRepository;
	
	@Autowired
	ProductService productService;

	@Override
	public Schema read(@RequestParam MultipartFile multipartFile, String _schema)
			throws JsonProcessingException, IOException {

		File file = multipartToFile(multipartFile);

		List<Map<String, Object>> response = new LinkedList<Map<String, Object>>();
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<Map<String, Object>> iterator = mapper.reader(Map.class).with(schema).readValues(file);

		Map<String, Object> schemaMap = new HashMap<String, Object>();

		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		while (iterator.hasNext()) {
			Map<String, Object> next = iterator.next();
			analyseSchema(next, schemaMap);

			data.add(next);
		}
		
		
		Schema schema2 = createSchema(_schema, schemaMap);
		
		schemaRepository.save(schema2);
		
		for (Map<String, Object> map : data) {
			DynamicObject dynamicObject = new DynamicObject(map);
			dynamicObject.setSchema(_schema);
			productService.addProduct(dynamicObject);
		}
		
		
		
		
		response.add(schemaMap);
		return schema2;
	}

	private Schema createSchema(String _schema, Map<String, Object> schemaMap) {
		
		Schema schemaObj = new Schema();
		schemaObj.setType(_schema);
		
		List<SchemaElement> elements = new ArrayList<>();
		
		Set<String> keySet = schemaMap.keySet();

		for (String key : keySet) {

			ElementType elementType = null;

			switch (schemaMap.get(key).toString()) {
			case "Integer":
				elementType = ElementType.INT;
				break;
			case "Double":
				elementType = ElementType.DOUBLE;
				break;
			case "Boolean":
				elementType = ElementType.BOOLEAN;
				break;
			case "String":
				elementType = ElementType.STRING;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + schemaMap.get(key).toString());
			}
			
			elements.add(new SchemaElement(key, elementType, false, null));
		}
		
		schemaObj.setElements(elements);
		
		return schemaObj;
		
	}

	private void analyseSchema(Map<String, Object> next, Map<String, Object> schemaMap) {

		Set<String> keySet = next.keySet();

		for (String key : keySet) {

			if (schemaMap.containsKey(key) && schemaMap.get(key) == "String")
				continue;

//			String value = next.get(key).toString();
//			try {
//				Integer valueInt = Integer.valueOf(value);
//				schemaMap.put(key, "Integer");
//				continue;
//			} catch (NumberFormatException e) {
//				System.out.println();
//			}
//			try {
//				Double valueDouble = Double.valueOf(value);
//				schemaMap.put(key, "Double");
//				continue;
//			} catch (NumberFormatException e) {
//				System.out.println();
//			}
//			if (value.equalsIgnoreCase("true")) {
//				schemaMap.put(key, "Boolean");
//				continue;
//			} else if (value.equalsIgnoreCase("false")) {
//				schemaMap.put(key, "Boolean");
//				continue;
//			}
			schemaMap.put(key, "String");

		}

	}

	private static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir") + "/temp.csv");
		multipart.transferTo(convFile);
		return convFile;
	}

}
