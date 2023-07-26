package com.comp303.lab4.testCentre;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Document(collection = "testcentre")
public class TestCentre {

	@Id
    private String _id;
	@NotBlank(message="TestCentre code is required")
    private String testCentreCode;
	@NotBlank(message="TestCentre name is required")
    private String testCentreName;

	@NotBlank(message="Street address is required")
    private String street;
	@NotBlank(message="City is required")
    private String city;
	@NotBlank(message="Province is required")
    private String province;
	@NotBlank(message="Phone number is required")
	@Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message="Phone number is invalid. Please use the format: 000-000-0000")
    private String phone;
	@NotBlank(message="Website is required")
    private String website;

    public TestCentre() {
        super();
    }

    public TestCentre(String _id, String testCentreCode, String testCentreName, String street, String city, String province,
            String phone, String website) {
        super();
        this._id = _id;
        this.testCentreCode = testCentreCode;
        this.testCentreName = testCentreName;
        this.street = street;
        this.city = city;
        this.province = province;
        this.phone = phone;
        this.website = website;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTestCentreCode() {
        return testCentreCode;
    }

    public void setTestCentreCode(String testCentreCode) {
        this.testCentreCode = testCentreCode;
    }

    public String getTestCentreName() {
        return testCentreName;
    }

    public void setTestCentreName(String testCentreName) {
        this.testCentreName = testCentreName;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
