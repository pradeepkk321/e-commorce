package com.kuriocity.ecom.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kuriocity.ecom.schema.Schema;

public interface DataUploadService {

	Schema read(MultipartFile multipartFile, String _schema) throws JsonProcessingException, IOException;

}
