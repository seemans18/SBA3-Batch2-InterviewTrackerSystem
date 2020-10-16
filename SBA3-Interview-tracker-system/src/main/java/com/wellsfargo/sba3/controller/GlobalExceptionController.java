package com.wellsfargo.sba3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wellsfargo.sba3.exception.UserException;

@RestControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handleContactException(UserException exp) {
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exp) {
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
