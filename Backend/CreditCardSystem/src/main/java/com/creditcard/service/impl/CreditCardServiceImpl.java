package com.creditcard.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.creditcard.dao.CreditCardDao;
import com.creditcard.exception.DBException;
import com.creditcard.exception.ServiceException;
import com.creditcard.model.request.CreditCard;
import com.creditcard.model.response.CreditCardResponse;
import com.creditcard.service.CreditCardService;
import com.creditcard.utils.ErrorCodes;
import com.creditcard.utils.StatusType;

/**
 * Class to manage Credit Service and its business logic
 * */
@Service
public class CreditCardServiceImpl implements CreditCardService {
	Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

	@Autowired
	CreditCardDao creditCardDao;

	/**
	 * Get All Credit Cards from system
	 * */
	@Override
	public CreditCardResponse getAllCreditCards() throws ServiceException {
		String errorMessage = "Null Response from DB";
		CreditCardResponse cardResponse = new CreditCardResponse();
		try {
			List<CreditCard> creditCards = creditCardDao.getAllCreditCards();
			if (creditCards == null) {
				cardResponse.setType(StatusType.ERROR);
				cardResponse.setStatusCode(ErrorCodes.NULL_RESPONSE_FROM_DB);
				cardResponse.setMessage(errorMessage);
				throw new ServiceException(errorMessage);
			} else {
				cardResponse.setType(StatusType.SUCCESS);
				cardResponse.setMessage("Successfully retrived Credit Card Data");
				cardResponse.setStatusCode(200);
				cardResponse.setData(creditCards);
			}

		} catch (DBException e) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(e.getMessage());
			cardResponse.setStatusCode(ErrorCodes.DB_EXCEPTION);
			e.printStackTrace();
			logger.info("Exception occured while fetching credit card details from DB "+e.getMessage());
		
		} catch (Exception e) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return cardResponse;

	}

	/*
	 * Adding Credit Card into System
	 * */
	@Override
	public CreditCardResponse addCreditCard(CreditCard creditCard) throws ServiceException {
		CreditCardResponse cardResponse = new CreditCardResponse();
		List<CreditCard> cards = new ArrayList<>();
		try {
			if (creditCard != null) {
				preConditionChecksBeforeCreditCardInsertion(creditCard, cardResponse, cards);
				CreditCard card = creditCardDao.addCreditCard(creditCard);
				cards.add(card);
				cardResponse.setType(StatusType.SUCCESS);
				cardResponse.setMessage("Successfully Added Credit Card Details");
				cardResponse.setStatusCode(200);
				cardResponse.setData(cards);
			} else {
				cardResponse.setType(StatusType.ERROR);
				cardResponse.setMessage("Credit Card Details cannot be empty");
				cardResponse.setData(cards);
				cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
				throw new ServiceException("Credit Card Details cannot be empty");
			}

		} catch (DBException e) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(e.getMessage());
			cardResponse.setStatusCode(ErrorCodes.DB_EXCEPTION);
			e.printStackTrace();
			logger.info("Exception occured while adding credit card from DB "+e.getMessage());
		
		} catch (Exception e) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(e.getMessage());
			e.printStackTrace();
			logger.info("Exception occured while adding credit card "+e.getMessage());
		}
		return cardResponse;
	}

	/**
	 * Precondition checks before adding credit card data into database
	 * */
	private void preConditionChecksBeforeCreditCardInsertion(CreditCard creditCard, CreditCardResponse cardResponse, List<CreditCard> cards) throws ServiceException, DBException {
		String creditCardNameError = "Name is Mandatory";
		String creditCardNumberError = "Credit Card Number is Mandatory";
		String creditCardLimitError = "Limit is Mandatory";
		String creditCardNumberExistsError = "Given Credit Card Number already existed in the system";
		String creditCardNumberIsIncorrect = "Given Credit Card Number is incorrect and should follow lunh 10 algoritham rules";
		String creditCardNumberLengthIncorrect = "Given Credit Card Number length should be less than 20";
		String creditCardNumberShouldNotBeNegative = "Given Credit Card Number should not be negative number";
		String creditCardLimitShouldNotBeNegative = "Given Credit Card Limit should not be negative number";
		
		if (StringUtils.isEmpty(creditCard.getName())) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardNameError);
			cardResponse.setData(cards);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			throw new ServiceException(creditCardNameError);
		} else if (creditCard.getCreditCardNumber() == null) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardNumberError);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			cardResponse.setData(cards);
			throw new ServiceException(creditCardNumberError);
		} else if (creditCard.getLimit() == null) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardLimitError);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			cardResponse.setData(cards);
			throw new ServiceException(creditCardLimitError);
		}
		if(creditCard.getLimit() < 0) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardLimitShouldNotBeNegative);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			cardResponse.setData(cards);
			throw new ServiceException(creditCardLimitShouldNotBeNegative);
		}
		if(String.valueOf(creditCard.getCreditCardNumber()).length()>19) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardNumberLengthIncorrect);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			cardResponse.setData(cards);
			throw new ServiceException(creditCardNumberLengthIncorrect);
		}
		if(creditCard.getCreditCardNumber() < 0) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardNumberShouldNotBeNegative);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			cardResponse.setData(cards);
			throw new ServiceException(creditCardNumberShouldNotBeNegative);
		}
		
		if (!luhnVerficationOnCardNumber(creditCard.getCreditCardNumber())) {
			cardResponse.setType(StatusType.ERROR);
			cardResponse.setMessage(creditCardNumberIsIncorrect);
			cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
			cardResponse.setData(cards);
			throw new ServiceException(creditCardNumberIsIncorrect);
		}
		List<CreditCard> creditCards = creditCardDao.getAllCreditCards();
		if (creditCards != null) {
			logger.info(creditCards.toString());
			for (CreditCard card : creditCards) {
				logger.info("card from DB" + card.getCreditCardNumber().toString());
				logger.info("card from input" + creditCard.getCreditCardNumber().toString());
				if (card.getCreditCardNumber().equals(creditCard.getCreditCardNumber())) {
					cardResponse.setType(StatusType.ERROR);
					cardResponse.setMessage(creditCardNumberExistsError);
					cardResponse.setStatusCode(ErrorCodes.INPUT_ERROR);
					cardResponse.setData(cards);
					throw new ServiceException(creditCardNumberExistsError);
				}
			}
		}
	}
	
	/**
	 * Luhn 10 algorithm. 
	 * 
	 * */
	public boolean luhnVerficationOnCardNumber(Long cardNumber) {
		String card = String.valueOf(cardNumber);
		int length = card.length();
		boolean secondNumber = false;
		int sum = 0;
		for (int i = length - 1; i >= 0; i--) {
			int digit = card.charAt(i) - '0';
			if (secondNumber == true) {
				digit = digit * 2;
			}

			sum += digit / 10;
			sum += digit % 10;
			secondNumber = !secondNumber;
		}
		boolean isDividesByTen = (sum % 10 == 0);
		return isDividesByTen;
	}

}
