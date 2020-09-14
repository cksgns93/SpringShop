package com.tis.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
public class NotUserException extends Exception {
	
	public NotUserException() {
		super("NotUserException");		
	}
	
	public NotUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
