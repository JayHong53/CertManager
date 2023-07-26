package com.comp303.lab4.authentication;

public class LoginResponse {
    private String _id;
    private String email;
    private String firstName;
    private String lastName; 

    public LoginResponse(String _id, String email, String firstName, String lastName) {
        this._id = _id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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
}


