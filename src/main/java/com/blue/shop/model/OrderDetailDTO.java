package com.blue.shop.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO implements Serializable {

	
	private int orderDetailId;
	
	@NotEmpty
	private int orderId;
	
	@NotEmpty
	private int productId;
	
	@NotEmpty
	private int quantity;
	
	@NotEmpty
	private double unitPrice;
}
