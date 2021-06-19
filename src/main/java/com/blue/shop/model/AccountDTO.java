package com.blue.shop.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

	@NotEmpty
	@Length(min = 6)
	private String username;
	
	@NotEmpty
	@Length(min = 6)
	private String password;
	

	private boolean admin;
	
	private Boolean isEdit = false;
}
