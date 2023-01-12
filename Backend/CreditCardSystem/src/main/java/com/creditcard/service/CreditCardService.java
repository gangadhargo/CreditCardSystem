package com.creditcard.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.creditcard.exception.ServiceException;
import com.creditcard.model.request.CreditCard;
import com.creditcard.model.response.CreditCardResponse;

/**
 * Credit Card Service Interface
 * */
@RestController
public interface CreditCardService {
	

	@GetMapping("/allCreditCards")
	public CreditCardResponse getAllCreditCards() throws ServiceException;

	@PostMapping("/addCreditCard")
	public CreditCardResponse addCreditCard(@RequestBody CreditCard creditCard) throws ServiceException;
}
