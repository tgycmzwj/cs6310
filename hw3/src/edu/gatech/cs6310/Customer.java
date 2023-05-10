package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

import java.util.HashMap;
import java.util.Map;

public class Customer extends User{
    private int rating;
    private int credit;
    private Map<String,Order> pendingOrdersMap;
    //constructor
    public Customer(String userID,String firstName,String lastName,String phoneNumber,int credit,int rating){
        this.UserID=userID;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.credit=credit;
        this.rating=rating;
        this.pendingOrdersMap=new HashMap<>();
    }

    //additional getter and setter function
    public int getCredit(){
        return this.credit;
    }
    public void setCredit(int credit){
        this.credit=credit;
    }
    public int getRating(){
        return this.rating;
    }
    public Map<String,Order> getPendingOrdersMap(){ return this.pendingOrdersMap; }

    //display function
    public void display(){
        System.out.println("name:"+this.getFirstName()+"_"+this.getLastName()+
                ",phone:"+this.getPhoneNumber()+",rating:"+this.getRating()+
                ",credit:"+this.getCredit());
    }

    //get pending charge
    public int getPendingCharge(){
        int pendingCharge=0;
        for (String orderID:this.getPendingOrdersMap().keySet()){
            pendingCharge=pendingCharge+this.getPendingOrdersMap().get(orderID).getOrderValue();
        }
        return pendingCharge;
    }
}
