package com.kuriocity.ecom.error_handler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kuriocity.ecom.schema.exception.SchemaMisMatchException;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@Value("${app.stacktrace.enable:false}")
	private boolean enableStackTrace;

	@ExceptionHandler(NullPointerException.class) // exception handled
	public ResponseEntity<ErrorResponse> handleNullPointerExceptions(Exception e) {
		// ... potential custom logic

		e.printStackTrace();
		
		HttpStatus status = HttpStatus.NOT_FOUND; // 404

		return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
	}

	// fallback method
	@ExceptionHandler(Exception.class) // exception handled
	public ResponseEntity<ErrorResponse> handleExceptions(Exception e) {

		e.printStackTrace();
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

		String stackTrace = getStackTrace(e);

		return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), stackTrace), status);
	}

	@ExceptionHandler(SchemaMisMatchException.class) // exception handled
	public ResponseEntity<ErrorResponse> handleSchemaMisMatchExceptions(Exception e) {

		e.printStackTrace();
		
		HttpStatus status = HttpStatus.BAD_REQUEST; // 400

		String stackTrace = getStackTrace(e);

		return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), stackTrace), status);
	}

	private String getStackTrace(Exception e) {

		if (!enableStackTrace)
			return null;

		// converting the stack trace to String
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		String stackTrace = stringWriter.toString();
		return stackTrace;
	}

}
