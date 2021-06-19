package com.blue.shop.model;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

	private Long productId;

	private String name;

	private int quantity;

	private double unitPrice;

	private String image;

	private MultipartFile imageFile;

	private String description;

	private double discount;

	private Date enDate;

	private short status;

	private Long categoryId;
	
	private Boolean isEdit;
}
