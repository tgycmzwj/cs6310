package edu.gatech.cs6310.entity;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "storeOrder")
public class Order {

    @EmbeddedId
    private final OrderId orderId;

    @Column(name = "customer_Id")
    private String customerId;

    @Column(name = "drone_id")
    private String droneId;

    @Column(name = "order_cost")
    private int orderCost;

    @Column(name = "order_weight")
    private int orderWeight;

    @Column(name = "status")
    private String status;

    @Column(name = "location_x")
    private int locationX;

    @Column(name = "location_y")
    private int locationY;

    @Column(name = "coupon_Id")
    private String couponId = null;

    @Column(name = "coupon_expiry")
    private LocalDateTime couponExpiry = null;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLines;

    public Order() {
        this.orderId = null;
    }

    public Order(OrderId orderId, String customerId, String droneId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.droneId = droneId;
        this.orderCost = 0;
        this.orderWeight = 0;
        this.status = "pending";
        this.locationX = 0;
        this.locationY = 0;
    }

    public int getLocationX() {
        return this.locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return this.locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public String getCouponId() {
        return couponId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getDroneId() {
        return droneId;
    }

    public int getOrderCost() {
        return orderCost;
    }

    public int getOrderWeight() {
        return orderWeight;
    }

    public LocalDateTime getCouponExpiry() {
        return couponExpiry;
    }

    private void updateOrderWeight(int lineWeight) {
        this.orderWeight += lineWeight;
    }

    private void updateOrderCost(int lineCost) {
        this.orderCost += lineCost;
    }

    public void addLine(int lineCost, int lineWeight) {
        updateOrderCost(lineCost);
        updateOrderWeight(lineWeight);
    }

    public String getStatus() {
        return this.status;
    }

    public void orderPaid() {
        this.status = "paid";
    }

    public void transferDrone(String newDroneId) {
        this.droneId = newDroneId;
    }

    public void displayOrder() {
        System.out.println("applied_couponID: " + this.couponId);
    }

    public static Comparator<Order> getComparator() {
        return Comparator.comparing(order -> order.couponExpiry);
    }

    /**
     * Sorts the orders by coupon expiry date with orders with no coupon at the end
     */
    public static void sortOrders(ArrayList<Order> orders) {
        ArrayList<Order> ordersWithCoupon = new ArrayList<>();
        for (Iterator<Order> i = orders.iterator(); i.hasNext(); ) {
            Order order = i.next();
            if (order.couponExpiry != null) {
                ordersWithCoupon.add(order);
                i.remove();
            }
        }
        ordersWithCoupon.sort(getComparator());
        orders.addAll(0, ordersWithCoupon);
    }

    public void addCoupon(String couponID, LocalDateTime couponExpiry) {
        this.couponId = couponID;
        this.couponExpiry = couponExpiry;
    }

    public void removeCoupon() {
        couponId = null;
        couponExpiry = null;
    }

    @Override
    public String toString() {
        assert orderId != null;
        if (couponId == null) {
            return orderId.getOrderId();
        } else {
            return orderId.getOrderId() + "*";
        }
    }
}
