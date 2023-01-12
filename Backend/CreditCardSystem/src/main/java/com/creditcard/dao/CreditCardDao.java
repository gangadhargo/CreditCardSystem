package com.creditcard.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.creditcard.exception.DBException;
import com.creditcard.model.request.CreditCard;

/**
 * Credit Card Dao Interface
 * */
@Component
public interface CreditCardDao {
	
	public List<CreditCard> getAllCreditCards() throws DBException;
	public CreditCard addCreditCard(CreditCard creditCard) throws DBException;
}
