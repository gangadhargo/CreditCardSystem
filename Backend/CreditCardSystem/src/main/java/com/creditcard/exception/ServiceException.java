package com.creditcard.exception;

import com.creditcard.model.response.StatusMessage;
/*
 * Custom Exception to handle Service Errors
 * */
public class ServiceException extends Exception{
	public ServiceException(String s) {
		super(s);
	}
	
	public ServiceException(StatusMessage s) {
		super(s.getMessage());
	}
	
}
