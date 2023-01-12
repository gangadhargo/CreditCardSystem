package com.creditcard;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.creditcard.dao.CreditCardDao;
import com.creditcard.exception.DBException;
import com.creditcard.exception.ServiceException;
import com.creditcard.model.request.CreditCard;
import com.creditcard.model.response.CreditCardResponse;
import com.creditcard.service.impl.CreditCardServiceImpl;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestMethodOrder(OrderAnnotation.class)
class CreditCardApplicationTests {

	
	@InjectMocks
	private CreditCardServiceImpl cardServiceImpl;
	
	@Mock
	private CreditCardDao creditCardDao;
	
	@Test
	@Order(1)
	@DisplayName("verify if card number passes checks of lunh 10 algoritham, if not verfiy error message")
	public void totalAmountWithOutOffer() throws ServiceException {
		CreditCard card = createCreditCard();
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getMessage().equals("Given Credit Card Number is incorrect and should follow lunh 10 algoritham rules"));
	}
	@Test
	@Order(2)
	@DisplayName("Check Given Credit Card Number is valid")
	public void validCard() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setCreditCardNumber(5610591081018250l);
		Boolean isValid = cardServiceImpl.luhnVerficationOnCardNumber(card.getCreditCardNumber());
		assertTrue(isValid);
	}
	
	@Test
	@Order(3)
	@DisplayName("Add Credit Card")
	public void addCreditCard() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setCreditCardNumber(79927398713l);
		when(creditCardDao.addCreditCard(card)).thenReturn(card);
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getData().get(0)!= null);
	}
	
	@Test
	@Order(4)
	@DisplayName("Verify error message on credit card number is negative")
	public void addCreditCardWithNegativeNumbers() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setCreditCardNumber(-1412343l);
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getMessage().equals("Given Credit Card Number should not be negative number"));
	}
	@Test
	@Order(5)
	@DisplayName("Verify error message on without credit card number")
	public void addCreditCardWithOutNumber() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setCreditCardNumber(null);
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getMessage().equals("Credit Card Number is Mandatory"));
	}
	@Test
	@Order(6)
	@DisplayName("Verify error message on without name while adding credit card")
	public void addCreditCardWithOutName() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setName(null);
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getMessage().equals("Name is Mandatory"));
	}
	
	@Test
	@Order(7)
	@DisplayName("Verify error message on without limit while adding credit card")
	public void addCreditCardWithOutLimit() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setLimit(null);
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getMessage().equals("Limit is Mandatory"));
	}
	@Test
	@Order(8)
	@DisplayName("Verify error message on credit card limit is negative")
	public void addCreditCardLimitWithNegativeNumbers() throws ServiceException, DBException {
		CreditCard card = createCreditCard();
		card.setLimit(-123d);
		CreditCardResponse cardResponse = cardServiceImpl.addCreditCard(card);
		assertTrue(cardResponse.getMessage().equals("Given Credit Card Limit should not be negative number"));
	}
	public CreditCard createCreditCard() {
		CreditCard card = new CreditCard();
		card.setCreditCardNumber(1234l);
		card.setName("Gangadhar");
		card.setLimit(1000d);
		return card;
	}

}
