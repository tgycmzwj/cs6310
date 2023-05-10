package edu.gatech.cs6310.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderLineId implements Serializable {

    @Column(name="store_name")
    private String storeName;

    @Column(name="order_id")
    private String orderId;

    @Column(name="item_name")
    private String itemName;


    public OrderLineId(String storeName, String orderId, String itemName) {
        this.storeName = storeName;
        this.orderId = orderId;
        this.itemName = itemName;
    }

    public OrderLineId() {

    }


    public String getItemName() {
        return itemName;
    }
}
