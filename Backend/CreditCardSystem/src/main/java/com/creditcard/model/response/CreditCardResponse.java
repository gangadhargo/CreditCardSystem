package com.creditcard.model.response;

import java.util.List;

import com.creditcard.model.request.CreditCard;


/**
 * Credit Card Response class
 * */
public class CreditCardResponse extends StatusMessage{

	private List<CreditCard> data;

	public List<CreditCard> getData() {
		return data;
	}

	public void setData(List<CreditCard> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CreditCardResponse [data=" + data + "]";
	}
	
}
