package edu.gatech.cs6310.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ItemId implements Serializable {

    @Column(name="store_name")
    private String storeName;

    @Column(name="item_name")
    private String itemName;

    public ItemId(String storeName, String itemName) {
        this.storeName = storeName;
        this.itemName = itemName;
    }

    public ItemId() {

    }

    public String getItemName() {
        return itemName;
    }
}
