package com.blue.shop.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	@Column(columnDefinition = "varchar(50) not null")
	private String name;
	
	@Column(columnDefinition = "varchar(100) not null")
	private String email;
	
	@Column(length = 75, nullable = false)
	private String password;
	
	@Column(length = 20)
	private String phone;
	
	@Column(length = 200)
	private String image;
	
	@Temporal(TemporalType.DATE)
	private Date resDate;
	
	@Column(nullable = false)
	private short status;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Order> orders;
}
