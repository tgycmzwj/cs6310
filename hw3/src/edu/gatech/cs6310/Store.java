package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Store {
    private Map<String,Drone> fleetDrones;
    private TreeMap<String,Order> pendingOrders;
    private TreeMap<String,Order> finishedOrders;
    private TreeMap<String,Item> catalog;
    private String storeName;
    private int totalRevenue;
    private int totalNumberTransfers;
    private int overLoads;
    //constructor
    public Store(String name,int totalRevenue){
        this.storeName=name;
        this.totalRevenue=totalRevenue;
        this.catalog=new TreeMap<>();
        this.fleetDrones=new HashMap<>();
        this.pendingOrders=new TreeMap<>();
        this.finishedOrders=new TreeMap<>();
        this.totalNumberTransfers=0;
    }
    //getter and setter function
    public Map<String,Drone> getFleetDrones(){
        return this.fleetDrones;
    }
    public Map<String,Item> getCatalog(){
        return this.catalog;
    }
    public Map<String,Order> getPendingOrders(){
        return this.pendingOrders;
    }
    public Map<String,Order> getFinishedOrders(){ return this.finishedOrders; }
    public int getTotalRevenue(){ return this.totalRevenue; }
    public void setTotalRevenue(int totalRevenue){
        this.totalRevenue=totalRevenue;
    }
    public int getTotalNumberTransfers(){ return this.totalNumberTransfers; }
    public void setTotalNumberTransfers(int totalNumberTransfers){ this.totalNumberTransfers=totalNumberTransfers; }
    public int getOverLoads(){ return this.overLoads; }
    public void setOverLoads(int overLoads){ this.overLoads=overLoads; }
    //display store information
    public void display(){
        System.out.println("name:"+this.storeName+",revenue:"+this.totalRevenue);
    }


    //add item to catalog
    public boolean addItem2Catalog(String itemName,int weight){
        //item already in the catalog of the store
        if (this.catalog.containsKey(itemName)){
            System.out.println("ERROR:item_identifier_already_exists");
            return false;
        }
        //successfully add the item to the catalog
        else{
            this.catalog.put(itemName,new Item(itemName,weight));
            System.out.println("OK:change_completed");
            return true;
        }
    }


    //display item
    public void displayItems(){
        for (String itemName: this.catalog.keySet()){
            System.out.println(itemName+","+this.catalog.get(itemName).getWeight());
        }
        System.out.println("OK:display_completed");
    }

    //display orders
    public void displayOrders(){
        for (String orderID:this.getPendingOrders().keySet()){
            this.getPendingOrders().get(orderID).display();
        }
        System.out.println("OK:display_completed");
    }

    //add drone to fleet
    public boolean addDrone2Fleet(String droneID,int weightCapacity,int refuelCapacity){
        //drone identifier already exist
        if (this.fleetDrones.containsKey(droneID)){
            System.out.println("ERROR:drone_identifier_already_exists");
            return false;
        }
        //successfully add the drone to fleet
        else {
            Drone curDrone=new Drone(droneID,weightCapacity,refuelCapacity);
            this.fleetDrones.put(droneID,curDrone);
            System.out.println("OK:change_completed");
            return true;
        }
    }

    //display drone fleet
    public void displayFleet(){
        for (String droneID:this.getFleetDrones().keySet()){
            this.getFleetDrones().get(droneID).display();
        }
        System.out.println("OK:display_completed");
    }

    //start a new order
    public void startOrder(String orderID,Customer currentCustomer, String droneID){
        //create a new order
        Order currentOrder=new Order(orderID, currentCustomer.getUserID(),droneID);
        //add this new order to the pending order list of the store
        this.getPendingOrders().put(orderID,currentOrder);
        //add this new order to the pending order list of the customer
        currentCustomer.getPendingOrdersMap().put(orderID,currentOrder);
        //add this new order to the drone
        this.getFleetDrones().get(droneID).getCurrentOrders().put(orderID,currentOrder);
    }

    //checkout the order
    public boolean checkoutOrder(String orderID,Customer currentCustomer){
        Order currentOrder=this.getPendingOrders().get(orderID);
        Drone currentDrone=this.getFleetDrones().get(currentOrder.getDroneID());
        //drone not assigned pilot
        if (currentDrone.getAssignedPilot()==null){
            System.out.println("ERROR:drone_needs_pilot");
            return false;
        }
        //drone needs fuel
        else if (currentDrone.getRefuelCapacityRemain()<=0) {
            System.out.println("ERROR:drone_needs_fuel");
            return false;
        }
        else {
            int totalCost=currentOrder.getOrderValue();
            Pilot currentPilot=currentDrone.getAssignedPilot();
            //update customer credit
            currentCustomer.setCredit(currentCustomer.getCredit()-totalCost);
            //update store revenue
            this.setTotalRevenue(this.getTotalRevenue()+totalCost);
            //update drone status
            currentDrone.setTotalNumberTrip(currentDrone.getTotalNumberTrip()+1);
            currentDrone.setRefuelCapacityRemain(currentDrone.getRefuelCapacityRemain()-1);
            currentDrone.setWeightCapacityRemain(currentDrone.getWeightCapacityRemain()+currentOrder.getOrderWeight());
            //calculate overloads
            this.setOverLoads(this.getOverLoads()+currentDrone.getCurrentOrders().size()-1);
            //remove order from drone
            currentDrone.getCurrentOrders().remove(currentOrder.getOrderID());
            //update pilot status
            currentPilot.setNumSuccessDelivery(currentPilot.getNumSuccessDelivery()+1);
            //remove order from pending orders and add to finished orders of the store
            this.getPendingOrders().remove(orderID);
            this.getFinishedOrders().put(orderID,currentOrder);
            //remove order from the pending orders list of the customer
            currentCustomer.getPendingOrdersMap().remove(orderID);
            return true;
        }
    }

    //cancel an order
    public boolean cancelOrder(String orderID,Customer currentCustomer){
        Order currentOrder=this.getPendingOrders().get(orderID);
        Drone currentDrone=this.getFleetDrones().get(currentOrder.getDroneID());
        //restore drone capacity
        int totalWeight=currentOrder.getOrderWeight();
        currentDrone.setWeightCapacityRemain(currentDrone.getWeightCapacityRemain()+totalWeight);
        //remove order from drone
        currentDrone.getCurrentOrders().remove(currentOrder.getOrderID());
        //remove order from store
        this.getPendingOrders().remove(currentOrder.getOrderID());
        //remove order from the customer
        currentCustomer.getPendingOrdersMap().remove(currentOrder.getOrderID());
        return true;
    }

    //transfer an order
    public void transferOrder2Drone(String orderID, String droneID){
        if (!this.getFleetDrones().containsKey(droneID)){
            System.out.println("ERROR:drone_identifier_does_not_exist");
        }
        else {
            Order currentOrder = this.getPendingOrders().get(orderID);
            Drone newDrone = this.getFleetDrones().get(droneID);
            Drone oldDrone = this.getFleetDrones().get(currentOrder.getDroneID());
            if (currentOrder.getOrderWeight() > newDrone.getWeightCapacityRemain()) {
                System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
            }

            else if (currentOrder.getDroneID().equals(droneID)) {
                System.out.println("OK:new_drone_is_current_drone_no_change");
            }
            else {
                //update old drone
                oldDrone.setWeightCapacityRemain(oldDrone.getWeightCapacityRemain()+currentOrder.getOrderWeight());
                oldDrone.getCurrentOrders().remove(orderID);
                //update new drone
                newDrone.setWeightCapacityRemain(newDrone.getWeightCapacityRemain()-currentOrder.getOrderWeight());
                newDrone.getCurrentOrders().put(orderID,currentOrder);
                //update order
                currentOrder.setDroneID(droneID);
                this.setTotalNumberTransfers(this.getTotalNumberTransfers()+1);
                System.out.println("OK:change_completed");
            }
        }
    }

    //display the efficiency
    public void displayEfficiency(){
        System.out.println("name:"+this.storeName+
                ",purchases:"+this.getFinishedOrders().size()+
                ",overloads:"+ this.getOverLoads()+
                ",transfers:"+this.getTotalNumberTransfers());
    }
}
