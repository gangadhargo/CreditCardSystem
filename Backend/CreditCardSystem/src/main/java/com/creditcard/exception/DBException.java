package com.creditcard.exception;

import com.creditcard.model.response.StatusMessage;

/*
 * Custom Exception to handle DB Errors
 * */
public class DBException extends Exception{
	public DBException(String s) {
		super(s);
	}
	
	public DBException(StatusMessage s) {
		super(s.getMessage());
	}
	
}
