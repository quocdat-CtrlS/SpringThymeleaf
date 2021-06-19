package com.blue.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

	
	private Long customerId;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String email;
	

	private String password;
	
	@NotEmpty
	private String phone;
	

	private Date resDate;

	private short status;
	
	private String image;
	
	private MultipartFile imageFile;
	
	private Boolean isEdit = false;
}
