package com.kuriocity.ecom.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kuriocity.ecom.schema.Schema;
import com.kuriocity.ecom.service.DataUploadService;

@RestController
@RequestMapping("/api")
public class DataUploadController {

	@Autowired
	DataUploadService dataUploadService;

	@PostMapping("/uploadCSV")
	public Schema read(@RequestParam MultipartFile multipartFile, @RequestParam String _schema)
			throws JsonProcessingException, IOException {
		return dataUploadService.read(multipartFile, _schema);
	}

}
