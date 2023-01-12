package com.creditcard.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.creditcard.dao.CreditCardDao;
import com.creditcard.exception.DBException;
import com.creditcard.model.request.CreditCard;

/**
 * 
 * Class to store, retrieve credit card details in DB
 * 
 * */
@Repository
public class CreditCardDaoImpl implements CreditCardDao {

	Logger logger = LoggerFactory.getLogger(CreditCardDaoImpl.class);

	//Using HashMap as In-memory DB
	private static HashMap<String, CreditCard> creditCardDB = new HashMap<>();

	/**
	 * Retrieving All Credit Card Details
	 * */
	@Override
	public List<CreditCard> getAllCreditCards() throws DBException {
		List<CreditCard> creditCards = new ArrayList<>();
		try {
			if (creditCardDB.values() != null) {
				creditCards = List.copyOf(creditCardDB.values());
				logger.info(creditCards.toString());
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		return creditCards;
	}

	/**
	 * Adding New Credit Card into system with initial balance as 0
	 * */
	@Override
	public CreditCard addCreditCard(CreditCard creditCard) throws DBException {
		try {
			if (creditCard != null) {
				String id = UUID.randomUUID().toString();
				creditCard.setId(id);
				creditCard.setBalance(0d);
				creditCardDB.put(id, creditCard);
				logger.info("Credit Card Data inserted successfully");
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}
		return creditCard;

	}

}
