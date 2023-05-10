package edu.gatech.cs6310.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "drone")
public class Drone {

    @EmbeddedId
    private final DroneId droneId;

    @Column(name = "pilot_id")
    private String pilotId;

    @Column(name = "num_of_orders")
    private int numOfOrders;

    @Column(name = "price_of_drone")
    private int dronePrice;

    @Column(name = "weight_capacity")
    private final int weightCapacity;

    @Column(name = "weight_capacity_remain")
    private int weightCapacityRemain;

    @Column(name = "fuel_capacity")
    private int fuelCapacity;

    @Column(name = "fuel_capacity_remain")
    private int fuelCapacityRemain;

    @Column(name = "maintenance_capacity")
    private int maintenanceCapacity;

    @Column(name = "maintenance_capacity_remain")
    private int maintenanceCapacityRemain;

    @Column(name = "charge_speed_day")
    private int chargeSpeedFuelPerTimeDay;

    @Column(name = "charge_speed_night")
    private int chargeSpeedFuelPerTimeNight;

    @Column(name = "speed")
    private int speedDistancePerTime;

    @Column(name = "fuel_per_distance")
    private int fuelPerDistance;

    @Column(name = "available_time")
    private LocalDateTime availableTime;

    @Column(name = "store_location_x")
    private int storeLocationX;

    @Column(name = "store_location_y")
    private int storeLocationY;

    @Column(name = "current_location_x")
    private int currentLocationX;

    @Column(name = "current_location_y")
    private int currentLocationY;

    public Drone() {
        this.weightCapacity = 0;
        this.droneId = null;
    }

    public Drone(DroneId droneId, int capacity, int fuelCapacity, int maintenanceCapacity) {
        this.droneId = droneId;
        this.weightCapacity = capacity;
        this.weightCapacityRemain = capacity;
        this.fuelCapacity = fuelCapacity;
        this.fuelCapacityRemain = fuelCapacity;
        this.maintenanceCapacity = maintenanceCapacity;
        this.maintenanceCapacityRemain = maintenanceCapacity;
        this.numOfOrders = 0;
        this.dronePrice = 0;
        this.chargeSpeedFuelPerTimeDay = 100;
        this.chargeSpeedFuelPerTimeNight = 100;
        this.fuelPerDistance = 1;
        this.speedDistancePerTime = 100;
        this.availableTime = null;
    }


    public void setDronePrice(int dronePrice) {
        this.dronePrice = dronePrice;
    }


    public void setChargeSpeedFuelPerTimeDay(int chargeSpeedFuelPerTimeDay) {
        this.chargeSpeedFuelPerTimeDay = chargeSpeedFuelPerTimeDay;
    }

    public void setChargeSpeedFuelPerTimeNight(int chargeSpeedFuelPerTimeNight) {
        this.chargeSpeedFuelPerTimeNight = chargeSpeedFuelPerTimeNight;
    }

    public int getMaintenanceCapacityRemain() {
        return maintenanceCapacityRemain;
    }

    public void setMaintenanceCapacityRemain(int maintenanceCapacityRemain) {
        this.maintenanceCapacityRemain = maintenanceCapacityRemain;
    }

    public void setSpeedDistancePerTime(int speedDistancePerTime) {
        this.speedDistancePerTime = speedDistancePerTime;
    }


    public void setFuelPerDistance(int fuelPerDistance) {
        this.fuelPerDistance = fuelPerDistance;
    }

    public int getStoreLocationX() {
        return storeLocationX;
    }

    public void setStoreLocationX(int storeLocationX) {
        this.storeLocationX = storeLocationX;
    }

    public int getStoreLocationY() {
        return storeLocationY;
    }

    public void setStoreLocationY(int storeLocationY) {
        this.storeLocationY = storeLocationY;
    }

    public int getCurrentLocationX() {
        return currentLocationX;
    }

    public void setCurrentLocationX(int currentLocationX) {
        this.currentLocationX = currentLocationX;
    }

    public int getCurrentLocationY() {
        return currentLocationY;
    }

    public void setCurrentLocationY(int currentLocationY) {
        this.currentLocationY = currentLocationY;
    }

    public LocalDateTime getAvailableTime() {
        return this.availableTime;
    }

    public void setAvailableTime(LocalDateTime availableTime) {
        this.availableTime = availableTime;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public String getPilotId() {
        return pilotId;
    }

    public void setPilot(String pilotId) {
        this.pilotId = pilotId;
    }

    public void unassignPilot() {
        this.pilotId = null;
    }

    public void updateRemainingCapacity(int weight) {
        this.weightCapacityRemain -= weight;
    }

    public void addOrder() {
        this.numOfOrders += 1;
    }

    public void removeOrder(int orderWeight) {
        this.numOfOrders -= 1;
        this.weightCapacityRemain += orderWeight;
    }

    public boolean checkMaintain() {
        return this.maintenanceCapacityRemain > 0;
    }

    public boolean checkCapacity(int weight) {
        return this.weightCapacityRemain >= weight;
    }

    private int calculateDistance(int x1, int y1, int x2, int y2) {

        //function to return the distance between two pairs of coordinates
        return (int) Math.sqrt(((double) x1 - (double) x2) * ((double) x1 - (double) x2) + ((double) y1 - (double) y2) * ((double) y1 - (double) y2));
    }

    private boolean isDayTime(LocalDateTime localDateTime) {
        //function to check if it is day time now
        //return: true---if day time; false---if night
        int currentHour = localDateTime.getHour();
        return currentHour >= 7 && currentHour < 19;
    }

    private int timeToDayNightSwitch(LocalDateTime localDateTime) {
        //day time---distance to 19:00PM
        if (isDayTime(localDateTime)) {
            return (int) localDateTime.until(localDateTime.with(LocalTime.of(19, 0)), ChronoUnit.MINUTES);
        }
        //night-time in the morning---distance to 7:00AM
        else if (localDateTime.getHour() < 7) {
            return (int) localDateTime.until(localDateTime.with(LocalTime.of(7, 0)), ChronoUnit.MINUTES);
        }
        //night-time in the afternoon---distance to 23:59PM+1+7*60
        else {
            return (int) localDateTime.until(localDateTime.with(LocalTime.of(23, 59)), ChronoUnit.MINUTES) + 60 * 7 + 1;
        }
    }

    public int travelTo(int destination_X, int destination_Y, LocalDateTime startTime) {
        //calculate the distance between current location and the destination
        int distance = calculateDistance(destination_X, destination_Y, this.getCurrentLocationX(), this.getCurrentLocationY());
        //a variable to record the total processing time flying to the destination
        int processingTime = 0;
        //as long as the remaining distance is still positive
        while (distance > 0) {
            //if still fuel remain
            if (this.fuelCapacityRemain > 0) {
                //the maximum distance can travel with the fuel remaining
                int distanceCapacity = this.fuelCapacityRemain / this.fuelPerDistance;
                //update fuel remain: the actual fuel remain is the maximum between 0 and the fuel remain if travel all remaining distance
                this.fuelCapacityRemain = Math.max(0, this.fuelCapacityRemain - distance * this.fuelPerDistance);
                //update time of travel: the travel distance is the minimal between remaining distance and the capacity distance
                processingTime = processingTime + Math.min(distanceCapacity, distance) / speedDistancePerTime;
                //update distance: the actual remaining distance is the maximum between 0 and previous remaining distance minus the maximum distance can travel
                distance = Math.max(0, distance - distanceCapacity);
            }
            //fuel used up, need to charge until full
            else {
                //use a while loop in case the charging period is long and involves day/night switching
                while (this.fuelCapacityRemain < this.fuelCapacity) {
                    //The charging time is partitioned into charging cycle, the partition point corresponds to day/night switch
                    //calculate the length of current charging cycle (time remaining to the next day/night switch)
                    int chargingTimeCurrentCycle = this.timeToDayNightSwitch(startTime.plusMinutes(processingTime));
                    //if it is day time now
                    if (isDayTime(startTime.plusMinutes(processingTime))) {
                        //update time of charging: by the minimal between: length of current charging cycle; and the time it takes to charge the drone to full
                        processingTime = processingTime + Math.min(chargingTimeCurrentCycle,
                                (this.fuelCapacity - this.fuelCapacityRemain) / this.chargeSpeedFuelPerTimeDay);
                        //update the fuel remain: by the minimal between: fuel capacity; and the amount of fuel charged in this cycle
                        this.fuelCapacityRemain = Math.min(this.fuelCapacity, this.fuelCapacityRemain + chargingTimeCurrentCycle * chargeSpeedFuelPerTimeDay);
                    }
                    //if it is nighttime now
                    else {
                        //update time of charging: by the minimal between: length of current charging cycle; and the time it takes to charge the drone to full
                        processingTime = processingTime + Math.min(chargingTimeCurrentCycle,
                                (this.fuelCapacity - this.fuelCapacityRemain) / this.chargeSpeedFuelPerTimeNight);
                        //update the fuel remain: by the minimal between: fuel capacity; and the amount of fuel charged in this cycle
                        this.fuelCapacityRemain = Math.min(this.fuelCapacity, this.fuelCapacityRemain + chargingTimeCurrentCycle * chargeSpeedFuelPerTimeNight);
                    }
                }
            }
        }
        //update the current location---now the drone is at the destination
        this.setCurrentLocationX(destination_X);
        this.setCurrentLocationY(destination_Y);
        //return total minutes needed to finish the travel
        return processingTime;
    }

    public void orderDelivered(ArrayList<Order> orders, LocalDateTime commendTime, Store store, HashMap<Order, Customer> orderCustomerHashMap) {
        //record the time starts to deliver these orders
        LocalDateTime startTime = commendTime;
        if (startTime.isBefore(this.getAvailableTime())) {
            startTime = this.getAvailableTime();
        }
        for (Order order : orders) {
            System.out.println("===" + "\n" + "on the way to deliver order: " + order);
            System.out.println("start location X: " + currentLocationX + ", Y: " + currentLocationY);
            //calculate the time needed to travel to the destination of the order
            int timeTo = travelTo(order.getLocationX(), order.getLocationY(), startTime);
            //update the starting time to deliver the next order
            startTime = startTime.plusMinutes(timeTo);
            //get the order delivered: remove these orders from the drone
            removeOrder(order.getOrderWeight());
            System.out.println("stop location  X: " + currentLocationX + ", Y: " + currentLocationY);
            System.out.println("==="  + "order delivered: " + order+"\n");
            LocalDateTime couponExpiry = order.getCouponExpiry();
            // apply penalty if the coupon is expired before the order is delivered
            if (couponExpiry != null && couponExpiry.isBefore(startTime)) {
                int penalty = (int) Math.round(0.1 * order.getOrderCost());
                orderCustomerHashMap.get(order).refund(penalty);
                store.updateRevenue(-penalty);
                System.out.println("coupon has expired (" + couponExpiry + ") before the order is delivered (" + startTime + ")");
                System.out.println("penalty applied: " + penalty);
            }
        }
        //calculate the time needed back to the store
        int backTime = travelTo(this.getStoreLocationX(), this.getStoreLocationY(), startTime);
        //update the available time of the drone
        this.setAvailableTime(startTime.plusMinutes(backTime));
        this.setMaintenanceCapacityRemain(this.getMaintenanceCapacityRemain() - 1);
        System.out.println("Drone is back to store at "+this.getAvailableTime());
    }

    public void receiveTransferredOrder(int weight) {
        addOrder();
        updateRemainingCapacity(weight);
    }

    public String displayDrone() {
        return "droneID:" + this.droneId.getIdentifier() + ",total_cap:" + this.weightCapacity + ",num_orders:" + this.numOfOrders + ",remaining_cap:" + this.weightCapacityRemain + ",trips_left:" + this.maintenanceCapacityRemain;
    }
}
