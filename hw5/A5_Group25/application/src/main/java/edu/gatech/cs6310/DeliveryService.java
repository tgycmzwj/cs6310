package edu.gatech.cs6310;

import edu.gatech.cs6310.dao.DeliveryServiceDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryService {

    public void commandLoop(DeliveryServiceDAO deliveryServiceDAO) {
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

                if (tokens[0].equals("make_store")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        int revenue = Integer.parseInt(tokens[2]);
                        int locationX = Integer.parseInt(tokens[3]);
                        int locationY = Integer.parseInt(tokens[4]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[5], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.makeStore(storeName, revenue, locationX, locationY);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter make_store,storeName(string),revenue(int),locationX(int),locationY(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_stores")) {
                    try {
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[1], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayStores();
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter display_stores,time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("sell_item")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        String itemName = tokens[2];
                        int weight = Integer.parseInt(tokens[3]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[4], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.sellItem(storeName, itemName, weight);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter sell_item,storeName(string),itemName(string),weight(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_items")) {
                    try {
                        // assign value
                        String storeName = tokens[1];
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[2], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayItems(storeName);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter display_items,storeName(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("make_pilot")) {
                    try {
                        // assign values
                        String account = tokens[1];
                        String firstName = tokens[2];
                        String lastName = tokens[3];
                        String phoneNumber = tokens[4];

                        String patternForPhone = "\\d{3}-\\d{3}-\\d{4}";
                        Pattern forPhone = Pattern.compile(patternForPhone);
                        Matcher mForPhone = forPhone.matcher(phoneNumber);
                        if (!mForPhone.matches()) {
                            System.out.println("ERROR:Please enter a phone number with a ten-digit 'xxx-xxx-xxxx' format.");
                            continue;
                        }

                        String taxId = tokens[5];
                        String patternForTax = "\\d{3}-\\d{2}-\\d{4}";
                        Pattern forTax = Pattern.compile(patternForTax);
                        Matcher mForTax = forTax.matcher(taxId);
                        if (!mForTax.matches()) {
                            System.out.println("ERROR:Please enter a taxId using 'xxx-xx-xxxx' format.");
                            continue;
                        }
                        String license = tokens[6];
                        int experience = Integer.parseInt(tokens[7]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[8], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.makePilot(account, firstName, lastName, phoneNumber, taxId, license, experience);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter make_pilot,account(string),firstname(string),lastname(string),phone(string:xxx-xxx-xxxx),taxId(string:xxx-xx-xxxx),license(string),experience(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_pilots")) {
                    try {
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[1], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayPilots();
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter display_pilots,time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("make_drone")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        String identifier = tokens[2];
                        int capacity = Integer.parseInt(tokens[3]);
                        int fuel = Integer.parseInt(tokens[4]);
                        int maintenanceCapacity = Integer.parseInt(tokens[5]);
                        int dronePrice = Integer.parseInt(tokens[6]);
                        int chargeSpeedDay = Integer.parseInt(tokens[7]);
                        int chargeSpeedNight = Integer.parseInt(tokens[8]);
                        int speed = Integer.parseInt(tokens[9]);
                        int fuelPerDistance = Integer.parseInt(tokens[10]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[11], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.makeDrone(storeName, identifier, capacity, fuel, maintenanceCapacity, dronePrice, chargeSpeedDay, chargeSpeedNight, speed, fuelPerDistance, commandTime);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter make_drone,storeName(string),droneId(string),capacity(int),fuel(int),maintenanceCapacity(int),dronePrice(int),chargeSpeedDay(int),chargeSpeedNight(int),speed(int),fuelPerDistance(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_drones")) {
                    try {
                        // assign value
                        String storeName = tokens[1];
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[2], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayDrones(storeName);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter display_drones,storeName(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("fly_drone")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        String droneId = tokens[2];
                        String pilotId = tokens[3];
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[4], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.flyDrone(storeName, droneId, pilotId);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter fly_drone,storeName(string),droneId(string),pilotId(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("make_customer")) {
                    try {
                        // assign values
                        String account = tokens[1];
                        String firstName = tokens[2];
                        String lastName = tokens[3];
                        String phoneNumber = tokens[4];
                        String patternForPhone = "\\d{3}-\\d{3}-\\d{4}";
                        Pattern forPhone = Pattern.compile(patternForPhone);
                        Matcher mForPhone = forPhone.matcher(phoneNumber);
                        if (!mForPhone.matches()) {
                            System.out.println("ERROR:Please enter a phone number with a ten-digit 'xxx-xxx-xxxx' format.");
                            continue;
                        }
                        int rating = Integer.parseInt(tokens[5]);
                        if (rating < 1 || rating > 5) {
                            System.out.println("ERROR:Please enter a customer rating between 1-5.");
                            continue;
                        }
                        int credit = Integer.parseInt(tokens[6]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[7], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.makeCustomer(account, firstName, lastName, phoneNumber, rating, credit);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter make_customer,account(string),firstname(string),lastname(string),phoneNumber(ddd-ddd-dddd),rating(int),credit(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_customers")) {
                    try {
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[1], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayCustomer();
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter display_customers,time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("start_order")) {
                    try {
                        // assign value
                        String storeName = tokens[1];
                        String orderId = tokens[2];
                        String droneId = tokens[3];
                        String customerId = tokens[4];
                        int locationX = Integer.parseInt(tokens[5]);
                        int locationY = Integer.parseInt(tokens[6]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[7], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.startOrder(storeName, orderId, droneId, customerId, locationX, locationY);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:please enter start_order,storeName(string),orderId(string),droneId(string),customerId(string),locationX(int),locationY(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_orders")) {
                    try {
                        // assign value
                        String storeName = tokens[1];
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[2], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayOrders(storeName);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:please enter display_order,storeName(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("request_item")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        String orderId = tokens[2];
                        String itemName = tokens[3];
                        int quantity = Integer.parseInt(tokens[4]);
                        int unitPrice = Integer.parseInt(tokens[5]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[6], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.requestItem(storeName, orderId, itemName, quantity, unitPrice);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter request_item,storeName(string),orderId(string),itemName(string),quantity(int),unitPrice(int),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("purchase_order")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        String orderId = tokens[2];
                        String[] orders;
                        orders = Arrays.copyOfRange(tokens, 2, tokens.length - 1);
                        List<String> orderList = new ArrayList<String>(Arrays.asList(orders));
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[tokens.length - 1], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.purchaseOrder(storeName, orders, commandTime);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter purchase_order,storeName(string),orderId1(string),orderId2(string),...orderIdN(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("transfer_order")) {
                    try {
                        // assign value
                        String storeName = tokens[1];
                        String orderId = tokens[2];
                        String newDroneId = tokens[3];
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[4], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.transferOrder(storeName, orderId, newDroneId, true);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter transfer_order,storeName(string),orderId(string),droneId(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("cancel_order")) {
                    try {
                        // assign value
                        String storeName = tokens[1];
                        String orderId = tokens[2];
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[3], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.cancelOrder(storeName, orderId);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter cancel_order,storeName(string),orderId(string),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("display_efficiency")) {
                    try {
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[1], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.displayEfficiency();
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter display_efficient,time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("make_coupon")) {
                    try {
                        String couponId = tokens[1];
                        DateTimeFormatter formatterExpiration = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate expirationDate = LocalDate.parse(tokens[2], formatterExpiration);
                        LocalDateTime expiration = expirationDate.atTime(23, 59, 00);

                        double discount = Double.parseDouble(tokens[3]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[4], formatter);
                        if (expiration.isAfter(commandTime)) {
                            if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                                deliveryServiceDAO.makeCoupon(couponId, expiration, discount);
                            }
                        } else {
                            System.out.println("ERROR:Please make sure that the coupon's expiration is after the command's time.");
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter make_coupon,couponId(string),expiration(yyyy-MM-dd),discount(double),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("distribute_coupon")) {
                    try {
                        String couponId = tokens[1];
                        double baseFrequency = Double.parseDouble(tokens[2]);
                        double higherPercentage = Double.parseDouble(tokens[3]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[4], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.distributeCoupon(couponId, baseFrequency, higherPercentage);
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR:Please enter distribute_coupon,couponId(string),baseFrequency(double),highFrequency(double),time(yyyy-MM-dd-HH-mm).");
                    }


                } else if (tokens[0].equals("available_commands")) {

                    System.out.println(
                            "   make_store,storeName(string),revenue(int),locationX(int),locationY(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_stores,time(yyyy-MM-dd-HH-mm)\n" +
                                    "   sell_item,storeName(string),itemName(string),weight(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_items,storeName(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   make_pilot,account(string),firstname(string),lastname(string),phone(string:xxx-xxx-xxxx),taxId(string:xxx-xx-xxxx),license(string),experience(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_pilots,time(yyyy-MM-dd-HH-mm)\n" +
                                    "   make_drone,storeName(string),droneId(string),capacity(int),fuel(int),maintenanceCapacity(int),dronePrice(int),chargeSpeedDay(int),chargeSpeedNight(int),speed(int),fuelPerDistance(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_drones,storeName(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   fly_drone,storeName(string),droneId(string),pilotId(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   make_customer,account(string),firstname(string),lastname(string),phoneNumber(ddd-ddd-dddd),rating(int),credit(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_customers,time(yyyy-MM-dd-HH-mm)\n" +
                                    "   start_order,storeName(string),orderId(string),droneId(string),customerId(string),locationX(int),locationY(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_orders,storeName(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   request_item,storeName(string),orderId(string),itemName(string),quantity(int),unitPrice(int),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   purchase_order,storeName(string),orderId1(string),orderId2(string),...orderIdN(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   cancel_order,storeName(string),orderId(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   transfer_order,storeName(string),orderId(string),droneId(string),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   display_efficient,time(yyyy-MM-dd-HH-mm)\n" +
                                    "   make_coupon,couponId(string),expiration(yyyy-MM-dd),discount(double),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   distribute_coupon,couponId(string),baseFrequency(double),highFrequency(double),time(yyyy-MM-dd-HH-mm)\n" +
                                    "   stop\n" +
                                    "end of commands\n" +
                                    "\nPlease enter a command:");


                } else if (tokens[0].equals("robustness_test")) {
                    try {
                        // assign values
                        String storeName = tokens[1];
                        int revenue = Integer.parseInt(tokens[2]);
                        int locationX = Integer.parseInt(tokens[3]);
                        int locationY = Integer.parseInt(tokens[4]);
                        LocalDateTime commandTime = LocalDateTime.parse(tokens[5], formatter);
                        if (deliveryServiceDAO.saveCommand(wholeInputLine, commandTime)) {
                            deliveryServiceDAO.robustness_test(storeName, revenue, locationX, locationY);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }


                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;


                } else {
                    if (tokens[0].startsWith("//")) {
                        continue;
                    } else {
                        System.out.println("command " + tokens[0] + " NOT acknowledged");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }

}
