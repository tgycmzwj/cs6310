package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

public class User {
    protected String UserID;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;

    // getter and setter function
    public String getUserID(){
        return this.UserID;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
}
