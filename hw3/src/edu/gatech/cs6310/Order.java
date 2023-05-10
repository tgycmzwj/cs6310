package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Order {
    private String orderID;
    private String customerID;
    private String droneID;
    private int orderValue;
    private int orderWeight;
    private Map<String,Item> itemMap;
    //constructor
    public Order(String orderID,String customerID,String droneID){
        this.orderID=orderID;
        this.customerID=customerID;
        this.droneID=droneID;
        this.orderValue=0;
        this.orderWeight=0;
        this.itemMap=new TreeMap<>();
    }
    //getter and setter function
    public String getOrderID(){
        return this.orderID;
    }
    public Map<String,Item> getItemMap(){
        return this.itemMap;
    }
    public String getCustomerID(){
        return this.customerID;
    }
    public String getDroneID(){
        return this.droneID;
    }
    public void setDroneID(String droneID){
        this.droneID=droneID;
    }
    public int getOrderValue(){
        return this.orderValue;
    }
    public void setOrderValue(int orderValue){
        this.orderValue=orderValue;
    }
    public int getOrderWeight(){
        return this.orderWeight;
    }
    public void setOrderWeight(int orderWeight){
        this.orderWeight=orderWeight;
    }

    //display
    public void display(){
        System.out.println("orderID:"+this.getOrderID());
        for (String itemID:this.getItemMap().keySet()){
            this.getItemMap().get(itemID).display();
        }
    }

    //add new item to the order
    public boolean addItem2Order(Item currentItem,int quantity,int unitPrice,Customer currentCustomer,Drone currentDrone){
        String itemName=currentItem.getItemName();
        int weight=currentItem.getWeight();
        //item already exists in the order
        if (this.itemMap.containsKey(itemName)){
            System.out.println("ERROR:item_already_ordered");
            return false;
        }
        else {
            int totalWeight=weight*quantity;
            int totalCost=quantity*unitPrice;
            //customer cannot afford
            if (currentCustomer.getCredit()<currentCustomer.getPendingCharge()+totalCost){
                System.out.println("ERROR:customer_cant_afford_new_item");
                return false;
            }
            //drone cannot carry the new item
            else if (currentDrone.getWeightCapacityRemain()<totalWeight) {
                System.out.println("ERROR:drone_cant_carry_new_item");
                return false;
            }
            //successfully add this new item to the order
            else {
                //set the price and quantity information
                Item currentItemCopy=new Item(itemName,weight);
                currentItemCopy.setQuantity(quantity);
                currentItemCopy.setUnitPrice(unitPrice);
                //add item line to the order
                this.getItemMap().put(itemName,currentItemCopy);
                //update the value of the order
                this.setOrderValue(this.getOrderValue()+totalCost);
                //update the weight of the order
                this.setOrderWeight(this.getOrderWeight()+totalWeight);
                //update the remaining capacity of the drone
                currentDrone.setWeightCapacityRemain(currentDrone.getWeightCapacityRemain()-totalWeight);
                return true;
            }
        }
    }


}
