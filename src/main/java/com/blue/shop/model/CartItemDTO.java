package com.blue.shop.model;



import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO implements Serializable {

	private int productId;
	private String name;
	private int quantity;
	private double unitPrice;
}
