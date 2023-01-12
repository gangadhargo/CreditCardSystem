package com.creditcard.model.request;

/**
 * 
 * Credit Card Class
 *
 */
public class CreditCard {
	
	private String id;
	private String name;
	private Long creditCardNumber;
	private Double limit; 
	private Double balance;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public Double getLimit() {
		return limit;
	}
	public void setLimit(Double limit) {
		this.limit = limit;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "CreditCard [id=" + id + ", name=" + name + ", creditCardNumber=" + creditCardNumber + ", limit=" + limit
				+ ", balance=" + balance + "]";
	} 
	
}
