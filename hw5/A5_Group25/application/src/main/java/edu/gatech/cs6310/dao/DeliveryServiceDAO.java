package edu.gatech.cs6310.dao;


import java.time.LocalDateTime;

public interface DeliveryServiceDAO {

    void makeStore(String storeName, int revenue, int locationX, int locationY);

    void displayStores();

    void sellItem(String storeName, String itemName, int weight);

    void displayItems(String storeName);

    void makePilot(String account, String firstName, String lastName, String phone, String taxId, String license, int experience);

    void displayPilots();

    void makeDrone(String storeName, String identifier, int capacity, int fuel, int maintain, int dronePrice, int chargeSpeedDay, int chargeSpeedNight, int speed, int fuelPerDistance, LocalDateTime creationTime);

    void displayDrones(String storeName);

    void flyDrone(String storeName, String droneId, String pilotId);

    void makeCustomer(String account, String firstName, String lastName, String phone, int rating, int credit);

    void displayCustomer();

    void startOrder(String storeName, String orderId, String droneId, String customerId, int locationX, int locationY);

    void displayOrders(String storeName);

    void requestItem(String storeName, String orderId, String itemName, int quantity, int unitPrice);

    //void purchaseOrder(String storeName, String orderId, LocalDateTime commandTime);
    void purchaseOrder(String storeName, String[] orderIds, LocalDateTime commandTime);

    void transferOrder(String storeName, String orderId, String newDroneId, boolean printMsg);

    void cancelOrder(String storeName, String orderId);

    void displayEfficiency();

    void makeCoupon(String couponId, LocalDateTime expiration, double discount);

    void distributeCoupon(String couponId, double baseFrequency, double highFrequency);

    boolean saveCommand(String commandContent, LocalDateTime commandTime);

    void robustness_test(String storeName, int revenue, int locationX, int locationY);
}
