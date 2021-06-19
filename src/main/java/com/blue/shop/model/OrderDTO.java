package com.blue.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

	
	private int orderId;
	
	@NotEmpty
	private Date orderDate;
	
	@NotEmpty
	private int customerId;
	
	@NotEmpty
	private double amount;
	
	@NotEmpty
	private short status;
}
