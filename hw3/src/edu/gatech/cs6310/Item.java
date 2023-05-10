package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

public class Item {
    private String itemName;
    private int weight;
    private int unitPrice;
    private int quantity;

    //constructor
    public Item(String itemName,int weight){
        this.itemName=itemName;
        this.weight=weight;
    }

    //getter and setter function
    //unit price
    public int getUnitPrice(){
        return this.unitPrice;
    }
    public void setUnitPrice(int unitPrice){
        this.unitPrice=unitPrice;
    }
    //quantity
    public int getQuantity(){
        return this.quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    //name
    public String getItemName(){
        return this.itemName;
    }
    //weight
    public int getWeight(){
        return this.weight;
    }
    public void display(){
        System.out.println("item_name:"+this.getItemName()+",total_quantity:"+this.getQuantity()+
                ",total_cost:"+this.getQuantity()*this.getUnitPrice()+
                ",total_weight:"+this.getQuantity()*this.getWeight());
    }

}
