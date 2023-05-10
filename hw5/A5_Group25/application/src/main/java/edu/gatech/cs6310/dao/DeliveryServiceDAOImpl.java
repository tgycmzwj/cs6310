package edu.gatech.cs6310.dao;

import edu.gatech.cs6310.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class DeliveryServiceDAOImpl implements DeliveryServiceDAO {

    private EntityManager entityManager;

    @Autowired
    public DeliveryServiceDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public boolean saveCommand(String commandContent, LocalDateTime commandTime) {
        //check stored queries
        TypedQuery<CommandBackUp> theQuery = entityManager.createQuery("SELECT c FROM CommandBackUp c ORDER BY commandTime DESC ", CommandBackUp.class);
        theQuery.setMaxResults(1);
        List<CommandBackUp> commandBackUpList = theQuery.getResultList();
        //check the time is correct
        if (commandBackUpList.size() > 0) {
            LocalDateTime commandTimeExisted = commandBackUpList.get(0).getCommandTime();
            if (commandTime.isBefore(commandTimeExisted.plusSeconds(1))) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println("Latest system time is " + commandTimeExisted.plusSeconds(1).format(formatter) + ", your command is before the existing time.");
                return false;
            }
        }
        CommandBackUp commandBackUp = new CommandBackUp(commandTime, commandContent);
        entityManager.persist(commandBackUp);
        return true;
    }

    @Override
    @Transactional
    public void makeStore(String storeName, int revenue, int locationX, int locationY) {
        // check if store exists
        if (storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_already_exists");
        } else {
            Store store = new Store(storeName, revenue);
            store.setLocationX(locationX);
            store.setLocationY(locationY);
            entityManager.persist(store);
            System.out.println("OK:change_completed");
        }
    }


    @Override
    public void displayStores() {
        TypedQuery<Store> theQuery = entityManager.createQuery("FROM Store", Store.class);
        List<Store> storeList = theQuery.getResultList();
        for (Store store : storeList) {
            System.out.println(store.displayStore());
        }
        System.out.println("OK:display_completed");
    }


    @Override
    @Transactional
    public void sellItem(String storeName, String itemName, int weight) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            if (itemExists(storeName, itemName)) {
                System.out.println("ERROR:item_identifier_already_exists");
            } else {
                Item item = new Item(new ItemId(storeName, itemName), weight);
                entityManager.persist(item);
                System.out.println("OK:change_completed");
            }
        }
    }

    @Override
    public void displayItems(String storeName) {
        // check if store exists, if yes, print all items for that store
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            TypedQuery<Item> theQuery = entityManager.createQuery("FROM Item WHERE itemId.storeName=:theStore", Item.class);
            theQuery.setParameter("theStore", storeName);
            List<Item> itemList = theQuery.getResultList();
            for (Item item : itemList) {
                System.out.println(item.displayItem());
            }
            System.out.println("OK:display_completed");
        }
    }


    @Override
    @Transactional
    public void makePilot(String account, String firstName, String lastName, String phone, String taxId, String license, int experience) {
        // to check if pilot (identifier and licenseId) exists in the system or not, if not, add pilot to the system
        if (pilotExists(account)) {
            System.out.println("ERROR:pilot_identifier_already_exists");
        } else {
            if (licenseExists(license)) {
                System.out.println("ERROR:pilot_license_already_exists");
            } else {
                Pilot pilot = new Pilot(account, firstName, lastName, phone, taxId, license, experience);
                entityManager.persist(pilot);
                System.out.println("OK:change_completed");
            }
        }
    }


    @Override
    public void displayPilots() {
        TypedQuery<Pilot> theQuery = entityManager.createQuery("FROM Pilot", Pilot.class);
        List<Pilot> pilotList = theQuery.getResultList();
        for (Pilot pilot : pilotList) {
            System.out.println(pilot.displayPilot());
        }
        System.out.println("OK:display_completed");
    }

    @Override
    @Transactional
    public void makeDrone(String storeName, String identifier, int capacity, int fuel, int maintain, int dronePrice, int chargeSpeedDay, int chargeSpeedNight, int speed, int fuelPerDistance, LocalDateTime creationTime) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            if (droneExists(storeName, identifier)) {
                System.out.println("ERROR:drone_identifier_already_exists");
            } else {
                Store store = entityManager.find(Store.class, storeName);
                Drone drone = new Drone(new DroneId(storeName, identifier), capacity, fuel, maintain);
                drone.setDronePrice(dronePrice);
                drone.setChargeSpeedFuelPerTimeDay(chargeSpeedDay);
                drone.setChargeSpeedFuelPerTimeNight(chargeSpeedNight);
                drone.setSpeedDistancePerTime(speed);
                drone.setFuelPerDistance(fuelPerDistance);
                drone.setStoreLocationX(store.getLocationX());
                drone.setStoreLocationY(store.getLocationY());
                drone.setCurrentLocationX(store.getLocationX());
                drone.setCurrentLocationY(store.getLocationY());
                drone.setAvailableTime(creationTime);
                //boolean initDrone = initializeDroneLocation(storeName, drone);
                entityManager.persist(drone);
                System.out.println("OK:change_completed");
            }
        }
    }


    @Override
    public void displayDrones(String storeName) {
        // check if store exists, if yes, print all drones belong to the store
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            TypedQuery<Drone> theQuery = entityManager.createQuery("FROM Drone WHERE droneId.storeName=:theStore", Drone.class);
            theQuery.setParameter("theStore", storeName);
            List<Drone> droneList = theQuery.getResultList();
            for (Drone drone : droneList) {
                String displayInfo = drone.displayDrone();
                // check if pilot is assigned to the drone or not, if yes, print pilot name
                String pilotId = drone.getPilotId();
                if (pilotId == null) {
                    System.out.println(displayInfo);
                } else {
                    Pilot pilot = entityManager.find(Pilot.class, pilotId);
                    System.out.println(displayInfo + ",flown_by:" + pilot.getName());
                }
            }
            System.out.println("OK:display_completed");
        }
    }


    @Override
    @Transactional
    public void flyDrone(String storeName, String droneId, String pilotId) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        }
        // check if drone exists
        else if (!droneExists(storeName, droneId)) {
            System.out.println("ERROR:drone_identifier_does_not_exist");
        }
        // check if pilot exists
        else if (!pilotExists(pilotId)) {
            System.out.println("ERROR:pilot_identifier_does_not_exist");
        } else {
            // check if drone currently has a pilot, if yes, disconnect drone and pilot
            Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
            String currentPilotId = drone.getPilotId();
            if (currentPilotId != null) {
                Pilot currentPilot = entityManager.find(Pilot.class, currentPilotId);
                currentPilot.offDuty();
            }
            // check if pilot is currently assigned to a drone, if yes, disconnect drone and pilot
            Pilot pilot = entityManager.find(Pilot.class, pilotId);
            String currentDroneId = pilot.getDroneId();
            if (currentDroneId != null) {
                Drone currentDrone = entityManager.find(Drone.class, new DroneId(pilot.getStoreName(), currentDroneId));
                currentDrone.unassignPilot();
            }
            // assign drone to pilot
            drone.setPilot(pilotId);
            pilot.onDuty(storeName, droneId);
            System.out.println("OK:change_completed");
        }
    }

    @Override
    @Transactional
    public void makeCustomer(String account, String firstName, String lastName, String phone, int rating, int credit) {
        if (customerExists(account)) {
            System.out.println("ERROR:customer_identifier_already_exists");
        } else {
            Customer customer = new Customer(account, firstName, lastName, phone, rating, credit);
            entityManager.persist(customer);
            System.out.println("OK:change_completed");
        }
    }

    @Override
    public void displayCustomer() {
        TypedQuery<Customer> theQuery = entityManager.createQuery("FROM Customer", Customer.class);
        List<Customer> customerList = theQuery.getResultList();
        for (Customer customer : customerList) {
            System.out.print(customer.displayCustomer());
            List<Coupon> coupons = customer.getCoupons();
            if (!coupons.isEmpty()) {
                System.out.print(",coupon:");
                for (Coupon coupon : coupons) {
                    System.out.print(coupon.getCouponId() + " ");
                }
            }
            System.out.println();

        }
        System.out.println("OK:display_completed");
    }


    @Override
    @Transactional
    public void startOrder(String storeName, String orderId, String droneId, String customerId, int locationX, int locationY) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
            return;
        }
        // check if orderId exists
        if (orderExists(storeName, orderId)) {
            System.out.println("ERROR:order_identifier_already_exists");
            return;
        }
        // check if drone exists
        if (!droneExists(storeName, droneId)) {
            System.out.println("ERROR:drone_identifier_does_not_exist");
            return;
        }
        if (!customerExists(customerId)) {
            System.out.println("ERROR:customer_identifier_does_not_exist");
            return;
        }

        Order order = new Order(new OrderId(storeName, orderId), customerId, droneId);
        order.setLocationX(locationX);
        order.setLocationY(locationY);
        // Apply coupon if customer has one, remove the coupon from customer's coupon list
        Customer customer = entityManager.find(Customer.class, customerId);
        List<Coupon> coupons = customer.getCoupons();
        // Only apply valid coupon if there is one
        TypedQuery<CommandBackUp> theQueryForCommand = entityManager.createQuery("SELECT c FROM CommandBackUp c ORDER BY commandTime DESC ", CommandBackUp.class);
        LocalDateTime systemTime = theQueryForCommand.getResultList().get(0).getCommandTime();
        if (!coupons.isEmpty()) {
            customer.sortAndSelectValidCoupons(systemTime);
            Coupon coupon = coupons.get(0);
            order.addCoupon(coupon.getCouponId(), coupon.getExpiration());
            coupons.remove(coupon);
        }

        entityManager.persist(order);
        Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
        drone.addOrder();
        System.out.println("OK:change_completed");
    }

    @Override
    public void displayOrders(String storeName) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        } else {
            TypedQuery<Order> theQueryOrder = entityManager.createQuery("FROM Order WHERE orderId.storeName=:theStore AND status=:orderStatus", Order.class);
            theQueryOrder.setParameter("theStore", storeName);
            theQueryOrder.setParameter("orderStatus", "pending");
            List<Order> orderList = theQueryOrder.getResultList();
            for (Order order : orderList) {
                String theOrderId = order.getOrderId().getOrderId();
                System.out.println("orderID:" + theOrderId);
                // check if order contains items, if yes, print the all
                TypedQuery<OrderLine> theQueryLine = entityManager.createQuery("FROM OrderLine WHERE orderLineId.storeName=:theStore AND orderLineId.orderId=:theOrder", OrderLine.class);
                theQueryLine.setParameter("theStore", storeName);
                theQueryLine.setParameter("theOrder", theOrderId);
                List<OrderLine> orderLineList = theQueryLine.getResultList();
                order.displayOrder();
                for (OrderLine orderLine : orderLineList) {
                    System.out.println(orderLine.displayOrderLine());
                }
            }
            System.out.println("OK:display_completed");
        }
    }


    @Override
    @Transactional
    public void requestItem(String storeName, String orderId, String itemName, int quantity, int unitPrice) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
            return;
        }
        // check if orderId exists
        if (!orderExists(storeName, orderId)) {
            System.out.println("ERROR:order_identifier_does_not_exists");
            return;
        }
        // check if item exists
        if (!itemExists(storeName, itemName)) {
            System.out.println("ERROR:item_identifier_does_not_exist");
            return;
        }
        // check if item has already been added
        if (lineExists(storeName, orderId, itemName)) {
            System.out.println("ERROR:item_already_ordered");
            return;
        }
        Item item = entityManager.find(Item.class, new ItemId(storeName, itemName));
        int itemWeight = item.getItemWeight();
        int lineCost = quantity * unitPrice;
        int lineWeight = quantity * itemWeight;
        Order order = entityManager.find(Order.class, new OrderId(storeName, orderId));
        String droneId = order.getDroneId();
        Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
        String customerId = order.getCustomerId();
        Customer customer = entityManager.find(Customer.class, customerId);
        // check if customer has enough credits
        if (!customer.checkCredit(lineCost)) {
            System.out.println("ERROR:customer_cant_afford_new_item");
            return;
        }
        // check if drone has sufficient capacity
        if (!drone.checkCapacity(lineWeight)) {
            System.out.println("ERROR:drone_cant_carry_new_item");
            return;
        }
        // add item to order, update total order cost for customer and remaining capacity for drone
        OrderLine orderLine = new OrderLine(new OrderLineId(storeName, orderId, itemName), quantity, unitPrice, itemWeight);
        entityManager.persist(orderLine);
        customer.updateTotalOrderCost(lineCost);
        drone.updateRemainingCapacity(lineWeight);
        order.addLine(lineCost, lineWeight);
        System.out.println("OK:change_completed");
    }


    @Override
    @Transactional
    public void purchaseOrder(String storeName, String[] orderIds, LocalDateTime commandTime) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
            return;
        }
        // key: droneID, value: list of order
        Map<String, ArrayList<Order>> drone2OrdersMap = new HashMap<String, ArrayList<Order>>();
        for (String orderId : orderIds) {
            // check if orderId exists
            if (!orderExists(storeName, orderId)) {
                System.out.println("ERROR:order_identifier_does_not_exists");
                return;
            }
            Order order = entityManager.find(Order.class, new OrderId(storeName, orderId));
            if (order.getStatus().equals("paid")) {
                System.out.println("ERROR:order_identifier_does_not_exists");
                return;
            }
            String droneId = order.getDroneId();
            Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
            String pilotId = drone.getPilotId();
            // check if drone has pilot
            if (pilotId == null) {
                System.out.println("ERROR:drone_needs_pilot");
                return;
            }
            // check if drone has enough maintenance to deliver all the orders
            if (!drone.checkMaintain()) {
                System.out.println("ERROR:drone_needs_maintenance");
                return;
            }
            if (!drone2OrdersMap.containsKey(droneId)) {
                drone2OrdersMap.put(droneId, new ArrayList<Order>());
            }
            drone2OrdersMap.get(droneId).add(order);
        }

        for (String droneId : drone2OrdersMap.keySet()) {
            int totalCost = 0;
            ArrayList<Order> orders = drone2OrdersMap.get(droneId);

            // remove expired coupons
            for (Order order : orders) {
                if (order.getCouponId() != null &&
                        entityManager.find(Coupon.class, order.getCouponId()).validityOfCoupon(commandTime).equals("expired")) {
                    order.removeCoupon();
                    System.out.println("coupon in " + order + " has expired");
                }
            }

            // sort orders by coupon's expiration date
            Order.sortOrders(orders);
            System.out.println("deliver sequence for drone "+droneId+" is " + orders);
            for (Order order : orders) {
                // get system's status when an order is purchased
                String customerId = order.getCustomerId();
                int orderCost = order.getOrderCost();
                totalCost = totalCost + orderCost;
                String couponId = order.getCouponId();
                int orderCostAfterDiscount = orderCost;
                // if the order has an unexpired coupon
                if (couponId != null) {
                    Coupon coupon = entityManager.find(Coupon.class, couponId);
                    // reduce the order price for customer
                    orderCostAfterDiscount = (int) Math.round(orderCost * (1 - coupon.getDiscount()));
                }
                // reduce order cost from credit and total order cost for customer
                Customer customer = entityManager.find(Customer.class, customerId);
                customer.orderCompleted(orderCostAfterDiscount);
                // change the order status to be paid
                order.orderPaid();
            }
            Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));

            // create an order customer map to pass into orderDelivered method
            HashMap<Order, Customer> orderCustomerHashMap = new HashMap<>();
            for (Order order : orders) {
                orderCustomerHashMap.put(order, entityManager.find(Customer.class, order.getCustomerId()));
            }
            Store store = entityManager.find(Store.class, storeName);

            // update number of orders, remaining capacity, fuel for drone
            drone.orderDelivered(orders, commandTime, store, orderCustomerHashMap);
            // increase pilot's experience level
            Pilot pilot = entityManager.find(Pilot.class, drone.getPilotId());
            pilot.updateExperienceLevel();
            // update revenue, purchase, overloads for store
            int numOfOrders = drone.getNumOfOrders();
            store.orderPaid(totalCost, numOfOrders);
        }
        System.out.println("OK:change_completed");
    }


    @Override
    @Transactional
    public void transferOrder(String storeName, String orderId, String newDroneId, boolean printMsg) {
        // check if store exists
        if (!storeExists(storeName) && printMsg) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        }
        // check if orderId exists
        else if (!orderExists(storeName, orderId) && printMsg) {
            System.out.println("ERROR:order_identifier_dose_not_exists");
        }
        // check if drone exists
        else if (!droneExists(storeName, newDroneId) && printMsg) {
            System.out.println("ERROR:drone_identifier_does_not_exist");
        } else {
            Order order = entityManager.find(Order.class, new OrderId(storeName, orderId));
            String droneId = order.getDroneId();
            Drone newDrone = entityManager.find(Drone.class, new DroneId(storeName, newDroneId));
            int orderWeight = order.getOrderWeight();
            // check if new drone has enough capacity
            if (!newDrone.checkCapacity(orderWeight) && printMsg) {
                System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
            }
            // check if new drone is the current drone
            else if (Objects.equals(droneId, newDroneId) && printMsg) {
                System.out.println("OK:new_drone_is_current_drone_no_change");
            }
            // update status for two drones
            else {
                // assign order to new drone
                order.transferDrone(newDroneId);
                Drone currentDrone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
                // update remaining capacity and number of order for two drones
                currentDrone.removeOrder(orderWeight);
                newDrone.receiveTransferredOrder(orderWeight);
                // update transfers for store
                Store store = entityManager.find(Store.class, storeName);
                store.updateTransfers();
                if (printMsg) System.out.println("OK:change_completed");
            }
        }
    }

    @Override
    @Transactional
    public void cancelOrder(String storeName, String orderId) {
        // check if store exists
        if (!storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_does_not_exist");
        }
        // check if orderId exists
        else if (!orderExists(storeName, orderId)) {
            System.out.println("ERROR:order_identifier_dose_not_exists");
        } else {
            Order order = entityManager.find(Order.class, new OrderId(storeName, orderId));
            String customerId = order.getCustomerId();
            String droneId = order.getDroneId();
            int orderCost = order.getOrderCost();
            int orderWeight = order.getOrderWeight();
            // reduce order cost from total order cost
            Customer customer = entityManager.find(Customer.class, customerId);
            customer.updateTotalOrderCost(-orderCost);
            // update remaining capacity and number of order for drone
            Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
            drone.removeOrder(orderWeight);
            // remove order from system
            entityManager.remove(order);
            System.out.println("OK:change_completed");
        }
    }

    @Override
    public void displayEfficiency() {
        TypedQuery<Store> theQuery = entityManager.createQuery("FROM Store", Store.class);
        List<Store> storeList = theQuery.getResultList();
        for (Store store : storeList) {
            System.out.println(store.displayEfficiency());
        }
        System.out.println("OK:display_completed");
    }

    @Override
    @Transactional
    public void makeCoupon(String couponId, LocalDateTime expiration, double discount) {
        if (couponExists(couponId)) {
            System.out.println("ERROR:coupon_identifier_already_exists");
        } else {
            Coupon coupon = new Coupon(couponId, expiration, discount);
            entityManager.persist(coupon);
            System.out.println("OK:change_completed");
        }
    }

    @Override
    @Transactional
    public void distributeCoupon(String couponId, double baseFrequency, double highFrequency) {
        if (!couponExists(couponId)) {
            System.out.println("ERROR:coupon_identifier_does_not_exist");
            return;
        }
        // Only distribute valid coupon
        Coupon coupon = entityManager.find(Coupon.class, couponId);
        TypedQuery<CommandBackUp> theQueryForCommand = entityManager.createQuery("SELECT c FROM CommandBackUp c ORDER BY commandTime DESC ", CommandBackUp.class);
        LocalDateTime systemTime = theQueryForCommand.getResultList().get(0).getCommandTime();
        if (coupon.validityOfCoupon(systemTime).equals("valid")) {
            TypedQuery<Customer> theQuery = entityManager.createQuery("FROM Customer", Customer.class);
            List<Customer> customersList = theQuery.getResultList();
            List<Customer> pickedCustomers = coupon.pickCustomersForCoupon(customersList, coupon, baseFrequency, highFrequency);
            for (Customer customer : pickedCustomers) {
                customer.getCoupons().add(coupon);
            }
            System.out.println("OK:coupon_distributed");
        } else {
            coupon.setState();
            System.out.println("ERROR:coupon_" + couponId + "_has_expired");
        }
    }

    @Override
    @Transactional
    public void robustness_test(String storeName, int revenue, int locationX, int locationY) {
        // check if store exists
        if (storeExists(storeName)) {
            System.out.println("ERROR:store_identifier_already_exists");
        } else {
            Store store = new Store(storeName, revenue);
            store.setLocationX(locationX);
            store.setLocationY(locationY);
            entityManager.persist(store);
            Store storeDuplicate = new Store(storeName, revenue);
            storeDuplicate.setLocationX(locationX);
            storeDuplicate.setLocationY(locationY);
            entityManager.persist(storeDuplicate);
            System.out.println("OK:change_completed");
        }
    }

    private boolean storeExists(String storeName) {
        Store store = entityManager.find(Store.class, storeName);
        return store != null;
    }


    private boolean pilotExists(String account) {
        Pilot pilot = entityManager.find(Pilot.class, account);
        return pilot != null;
    }

    private boolean licenseExists(String license) {
        TypedQuery<Pilot> theQueryPilot = entityManager.createQuery("FROM Pilot WHERE licenseId=:theLicense", Pilot.class);
        theQueryPilot.setParameter("theLicense", license);
        List result = theQueryPilot.getResultList();
        return !result.isEmpty();
    }

    private boolean droneExists(String storeName, String droneId) {
        Drone drone = entityManager.find(Drone.class, new DroneId(storeName, droneId));
        return drone != null;
    }

    private boolean customerExists(String account) {
        Customer customer = entityManager.find(Customer.class, account);
        return customer != null;
    }

    private boolean orderExists(String storeName, String orderId) {
        Order order = entityManager.find(Order.class, new OrderId(storeName, orderId));
        return order != null;
    }

    private boolean lineExists(String storeName, String orderId, String itemName) {
        OrderLine orderLine = entityManager.find(OrderLine.class, new OrderLineId(storeName, orderId, itemName));
        return orderLine != null;
    }

    private boolean itemExists(String storeName, String itemName) {
        Item item = entityManager.find(Item.class, new ItemId(storeName, itemName));
        return item != null;
    }

    private boolean couponExists(String couponId) {
        Coupon coupon = entityManager.find(Coupon.class, couponId);
        return coupon != null;
    }
}
