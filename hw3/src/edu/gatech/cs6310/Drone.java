package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */

import java.util.HashMap;
import java.util.Map;
public class Drone {
    private String droneID;
    private int weightCapacity;
    private int weightCapacityRemain;
    private int refuelCapacity;
    private int refuelCapacityRemain;
    private Pilot assignedPilot;
    private int totalNumberTrip;
    //private int overLoad;
    private Map<String,Order> currentOrders;
    public Drone(String droneID,int weightCapacity,int refuelCapacity){
        this.droneID=droneID;
        this.weightCapacity=weightCapacity;
        this.weightCapacityRemain=weightCapacity;
        this.refuelCapacity=refuelCapacity;
        this.refuelCapacityRemain=refuelCapacity;
        this.assignedPilot=null;
        this.totalNumberTrip=0;
        this.currentOrders= new HashMap<>();
        //this.overLoad=0;
    }
    //getter and setter function
    public int getWeightCapacity(){
        return weightCapacity;
    }
    public int getRefuelCapacity(){
        return refuelCapacity;
    }
    public int getWeightCapacityRemain(){
        return this.weightCapacityRemain;
    }
    public void setWeightCapacityRemain(int weightCapacityRemain){
        this.weightCapacityRemain=weightCapacityRemain;
    }
    public int getRefuelCapacityRemain(){
        return this.refuelCapacityRemain;
    }
    public void setRefuelCapacityRemain(int refuelCapacityRemain){
        this.refuelCapacityRemain=refuelCapacityRemain;
    }
    public int getTotalNumberTrip(){
        return this.totalNumberTrip;
    }
    public void setTotalNumberTrip(int totalNumberTrip){
        this.totalNumberTrip=totalNumberTrip;
    }
    public Pilot getAssignedPilot(){
        return assignedPilot;
    }
    public void setAssignedPilot(Pilot assignedPilot){
        this.assignedPilot=assignedPilot;
    }
    public Map<String,Order> getCurrentOrders(){
        return this.currentOrders;
    }
    //public int getOverLoad(){ return this.overLoad; }
    //public void setOverLoad(int overLoad){ this.overLoad=overLoad+1; }

    public void display(){
        if (this.assignedPilot==null){
            System.out.println("droneID:"+droneID+",total_cap:"+this.getWeightCapacity()+
                    ",num_orders:"+this.getCurrentOrders().size()+",remaining_cap:"+this.getWeightCapacityRemain()+
                    ",trips_left:"+this.getRefuelCapacityRemain());
        }
        else{
            System.out.println("droneID:"+droneID+",total_cap:"+this.getWeightCapacity()+
                    ",num_orders:"+this.getCurrentOrders().size()+",remaining_cap:"+this.getWeightCapacityRemain()+
                    ",trips_left:"+this.getRefuelCapacityRemain()+",flown_by:"+this.getAssignedPilot().getFirstName()+"_"+this.getAssignedPilot().getLastName());
        }
    }
}
