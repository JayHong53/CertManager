package com.comp303.lab4.candidate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Document(collection = "candidate")
public class Candidate {
	 
	@Id
	private String _id;
	
	@NotBlank(message="Email is required")
	@Email(message="Invalid email format")
	private String email;
	@NotBlank(message="Password is required")
	private String password;
	
	@NotBlank(message="Firstname is required")
	private String firstName;
	@NotBlank(message="Lastname is required")
	private String lastName;
	
	private String street;
	private String city;
	private String province;
	
	@NotBlank(message="Phone number is required")
	@Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message="Phone number is invalid. Please use the format: 000-000-0000")
	private String phone;
	
	public Candidate() {
		super();
	}
	
	public Candidate(String _id, String email, String password, String firstName, String lastName, String street,
			String city, String province, String phone) {
		super();
		this._id = _id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.province = province;
		this.phone = phone;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	
}
