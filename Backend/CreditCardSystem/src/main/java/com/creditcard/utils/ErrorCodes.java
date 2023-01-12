package com.creditcard.utils;

/**
 * 
 * Handling errors with specific error codes to identify specific issue type which eases developer debugging
 * */
public interface ErrorCodes {
	public static final Integer INPUT_ERROR = 800;
	public static final Integer NULL_RESPONSE_FROM_DB = 700;
	public static final Integer DB_EXCEPTION = 701;
}
