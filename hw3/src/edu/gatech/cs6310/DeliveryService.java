package edu.gatech.cs6310;
import java.util.*;
import java.util.Scanner;

public class DeliveryService {

    private Map<String,Store> storeMap;
    private Map<String,Pilot> idPilotMap;
    private Map<String,Pilot> licensePilotMap;
    private Map<String,Customer> idCustomerMap;
    //constructor
    public DeliveryService(){
        this.storeMap=new TreeMap<>();
        this.idPilotMap=new TreeMap<>();
        this.licensePilotMap=new TreeMap<>();
        this.idCustomerMap=new TreeMap<>();
    }
    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);


                if (tokens[0].equals("make_store")) {
                    //parse token
                    String storeName=tokens[1];
                    int totalRevenue=Integer.valueOf(tokens[2]);
                    //store not exists
                    if (this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_already_exists");
                    }
                    else {
                        this.storeMap.put(storeName,new Store(storeName,totalRevenue));
                        System.out.println("OK:change_completed");
                    }
                }


                else if (tokens[0].equals("display_stores")) {
                    for (String storeName:this.storeMap.keySet()){
                        this.storeMap.get(storeName).display();
                    }
                    System.out.println("OK:display_completed");
                }


                else if (tokens[0].equals("sell_item")) {
                    //parse token
                    String storeName=tokens[1];
                    String itemName=tokens[2];
                    int weight=Integer.valueOf(tokens[3]);
                    //check if store name exists
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    else {
                        Store curStore=this.storeMap.get(storeName);
                        curStore.addItem2Catalog(itemName,weight);
                    }
                }


                else if (tokens[0].equals("display_items")) {
                    //parse token
                    String storeName=tokens[1];
                    //store name exists
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    else {
                        this.storeMap.get(storeName).displayItems();
                    }
                }


                else if (tokens[0].equals("make_pilot")) {
                    //parse token
                    String userID=tokens[1];
                    String firstName=tokens[2];
                    String lastName=tokens[3];
                    String phoneNumber=tokens[4];
                    String taxID=tokens[5];
                    String licenseID=tokens[6];
                    int numSuccessfulDelivery=Integer.valueOf(tokens[7]);
                    //pilot id already exist
                    if (this.idPilotMap.containsKey(userID)){
                        System.out.println("ERROR:pilot_identifier_already_exists");
                    }
                    //pilot license already exist
                    else if (this.licensePilotMap.containsKey(licenseID)){
                        System.out.println("ERROR:pilot_license_already_exists");
                    }
                    else {
                        Pilot currentPilot=new Pilot(userID,firstName,lastName,phoneNumber,taxID,licenseID,numSuccessfulDelivery);
                        this.idPilotMap.put(userID,currentPilot);
                        this.licensePilotMap.put(licenseID,currentPilot);
                        System.out.println("OK:change_completed");
                    }
                }


                else if (tokens[0].equals("display_pilots")) {
                    for (String userID:this.idPilotMap.keySet()){
                        idPilotMap.get(userID).display();
                    }
                    System.out.println("OK:display_completed");
                }


                else if (tokens[0].equals("make_drone")) {
                    //parse token
                    String storeName=tokens[1];
                    String droneID=tokens[2];
                    int weightCapacity=Integer.valueOf(tokens[3]);
                    int refuelCapacity=Integer.valueOf(tokens[4]);
                    //check if store exists
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    else {
                        Store curStore=this.storeMap.get(storeName);
                        curStore.addDrone2Fleet(droneID,weightCapacity,refuelCapacity);
                    }
                }


                else if (tokens[0].equals("display_drones")) {
                    //parse token
                    String storeName=tokens[1];
                    //store id not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    else {
                        this.storeMap.get(storeName).displayFleet();
                    }
                }


                else if (tokens[0].equals("fly_drone")) {
                    //parse token
                    String storeName=tokens[1];
                    String droneID=tokens[2];
                    String pilotUserID=tokens[3];
                    //store does not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    //drone does not exist
                    else if (!this.storeMap.get(storeName).getFleetDrones().containsKey(droneID)){
                        System.out.println("ERROR:drone_identifier_does_not_exist");
                    }
                    //pilot does not exist -------does pilot has to be associated with particular store
                    else if (!this.idPilotMap.containsKey(pilotUserID)){
                        System.out.println("ERROR:pilot_identifier_does_not_exist");
                    }
                    else {
                        Drone currentDrone=this.storeMap.get(storeName).getFleetDrones().get(droneID);
                        Pilot currentPilot=this.idPilotMap.get(pilotUserID);
                        if (currentPilot.getAssignedDrone()!=null){
                            currentPilot.getAssignedDrone().setAssignedPilot(null);
                        }
                        currentDrone.setAssignedPilot(currentPilot);
                        currentPilot.setAssignedDrone(currentDrone);
                        System.out.println("OK:change_completed");
                    }
                }


                else if (tokens[0].equals("make_customer")) {
                    //parse token
                    String userID=tokens[1];
                    String firstName=tokens[2];
                    String lastName=tokens[3];
                    String phoneNumber=tokens[4];
                    int rating=Integer.valueOf(tokens[5]);
                    int credit=Integer.valueOf(tokens[6]);
                    //already exists
                    if (this.idCustomerMap.containsKey(userID)){
                        System.out.println("ERROR:customer_identifier_already_exists");
                    }
                    else {
                        Customer currentCustomer=new Customer(userID,firstName,lastName,phoneNumber,credit,rating);
                        this.idCustomerMap.put(userID,currentCustomer);
                        System.out.println("OK:change_completed");
                    }
                }


                else if (tokens[0].equals("display_customers")) {
                    for (String userID:this.idCustomerMap.keySet()){
                        this.idCustomerMap.get(userID).display();
                    }
                    System.out.println("OK:display_completed");
                }


                else if (tokens[0].equals("start_order")) {
                    //parse token
                    String storeName=tokens[1];
                    String orderID=tokens[2];
                    String droneID=tokens[3];
                    String customerUserID=tokens[4];
                    //store not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    //order already created
                    else if (this.storeMap.get(storeName).getPendingOrders().containsKey(orderID)) {
                        System.out.println("ERROR:order_identifier_already_exists");
                    }
                    //drone does not exist
                    else if (!this.storeMap.get(storeName).getFleetDrones().containsKey(droneID)) {
                        System.out.println("ERROR:drone_identifier_does_not_exist");
                    }
                    //customer not exist
                    else if (!this.idCustomerMap.containsKey(customerUserID)){
                        System.out.println("ERROR:customer_identifier_does_not_exist");
                    }
                    else {
                        Customer currentCustomer=this.idCustomerMap.get(customerUserID);
                        this.storeMap.get(storeName).startOrder(orderID,currentCustomer,droneID);
                        System.out.println("OK:change_completed");
                    }
                }


                else if (tokens[0].equals("display_orders")) {
                    //parse token
                    String storeName=tokens[1];
                    //store not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    else {
                        this.storeMap.get(storeName).displayOrders();
                    }
                }


                else if (tokens[0].equals("request_item")) {
                    //parse token
                    String storeName=tokens[1];
                    String orderID=tokens[2];
                    String itemName=tokens[3];
                    int quantity=Integer.valueOf(tokens[4]);
                    int unitPrice=Integer.valueOf(tokens[5]);
                    //store name not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    //order id not exist
                    else if (!this.storeMap.get(storeName).getPendingOrders().containsKey(orderID)) {
                        System.out.println("ERROR:order_identifier_does_not_exist");
                    }
                    //item does not exist
                    else if (!this.storeMap.get(storeName).getCatalog().containsKey(itemName)) {
                        System.out.println("ERROR:item_identifier_does_not_exist");
                    }
                    else {
                        Order currentOrder=this.storeMap.get(storeName).getPendingOrders().get(orderID);
                        Customer currentCustomer=idCustomerMap.get(currentOrder.getCustomerID());
                        Item currentItem=this.storeMap.get(storeName).getCatalog().get(itemName);
                        Drone currentDrone=this.storeMap.get(storeName).getFleetDrones().get(currentOrder.getDroneID());
                        //customer cannot afford this
                        if (currentOrder.addItem2Order(currentItem,quantity,unitPrice,currentCustomer,currentDrone)){
                            System.out.println("OK:change_completed");
                        }
                    }
                }


                else if (tokens[0].equals("purchase_order")) {
                    //parse token
                    String storeName=tokens[1];
                    String orderID=tokens[2];
                    //store id not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    //order id not exist
                    else if (!this.storeMap.get(storeName).getPendingOrders().containsKey(orderID)) {
                        System.out.println("ERROR:order_identifier_does_not_exist");
                    }
                    else {
                        Store currentStore=this.storeMap.get(storeName);
                        Order currentOrder=currentStore.getPendingOrders().get(orderID);
                        Customer currentCustomer=this.idCustomerMap.get(currentOrder.getCustomerID());
                        if (currentStore.checkoutOrder(orderID,currentCustomer)){
                            System.out.println("OK:change_completed");
                        }
                    }
                }


                else if (tokens[0].equals("cancel_order")) {
                    //parse token
                    String storeName=tokens[1];
                    String orderID=tokens[2];
                    //store id not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    //order id not exist
                    else if (!this.storeMap.get(storeName).getPendingOrders().containsKey(orderID)) {
                        System.out.println("ERROR:order_identifier_does_not_exist");
                    }
                    else {
                        Store currentStore=this.storeMap.get(storeName);
                        Order currentOrder=currentStore.getPendingOrders().get(orderID);
                        Drone currentDrone=currentStore.getFleetDrones().get(currentOrder.getDroneID());
                        Customer currentCustomer=this.idCustomerMap.get(currentOrder.getCustomerID());
                        if (currentStore.cancelOrder(orderID,currentCustomer)){
                            System.out.println("OK:change_completed");
                        }
                    }
                }


                else if (tokens[0].equals("transfer_order")) {
                    //parse token
                    String storeName=tokens[1];
                    String orderID=tokens[2];
                    String droneID=tokens[3];
                    //store id not exist
                    if (!this.storeMap.containsKey(storeName)){
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    //order id not exist
                    else if (!this.storeMap.get(storeName).getPendingOrders().containsKey(orderID)) {
                        System.out.println("ERROR:order_identifier_does_not_exist");
                    }
                    else {
                        Store currentStore=this.storeMap.get(storeName);
                        currentStore.transferOrder2Drone(orderID,droneID);
                    }
                }


                else if (tokens[0].equals("display_efficiency")) {
                    for (String storeName:this.storeMap.keySet()){
                        this.storeMap.get(storeName).displayEfficiency();
                    }
                    System.out.println("OK:display_completed");
                }


                else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;
                }


                else if (wholeInputLine.startsWith("//")){
                    continue;
                }


                else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            }


            catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
