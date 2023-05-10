package edu.gatech.cs6310.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderId implements Serializable {

    @Column(name="store_name")
    private String storeName;

    @Column(name="order_id")
    private String orderId;


    public OrderId() {

    }

    public OrderId(String storeName, String orderId) {
        this.storeName = storeName;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "OrderId{" +
                "storeName='" + storeName + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
