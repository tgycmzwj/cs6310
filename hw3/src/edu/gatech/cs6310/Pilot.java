package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

public class Pilot extends Employee{
    private String licenseID;
    private int numSuccessDelivery;
    private Drone assignedDrone;
    //constructor
    public Pilot(String userID,String firstName,String lastName,String phoneNumber,String taxID,String licenseID,int numSuccessDelivery){
        this.UserID=userID;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.taxID=taxID;
        this.licenseID=licenseID;
        this.numSuccessDelivery=numSuccessDelivery;
        this.assignedDrone=null;
    }
    //getter and setter
    public String getLicenseID(){
        return this.licenseID;
    }
    public Drone getAssignedDrone(){
        return this.assignedDrone;
    }
    public void setAssignedDrone(Drone assignedDrone){
        this.assignedDrone=assignedDrone;
    }
    public int getNumSuccessDelivery(){
        return this.numSuccessDelivery;
    }
    public void setNumSuccessDelivery(int numSuccessDelivery){
        this.numSuccessDelivery=numSuccessDelivery;
    }
    //display
    public void display(){
        System.out.println("name:"+this.getFirstName()+"_"+this.getLastName()+
                ",phone:"+this.getPhoneNumber()+",taxID:"+this.getTaxID()+
                ",licenseID:"+this.getLicenseID()+",experience:"+this.getNumSuccessDelivery());
    }
}
