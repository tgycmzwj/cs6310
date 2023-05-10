package edu.gatech.cs6310.entity;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @Column(name="account")
    protected final String account;

    @Column(name="first_name")
    protected final String firstName;

    @Column(name="last_name")
    protected final String lastName;

    @Column(name="phone_number")
    protected String phoneNumber;

    public User(){
        this.account = "";
        this.firstName = "";
        this.lastName = "";
    }
    public User(String account, String firstName, String lastName, String phoneNumber) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
