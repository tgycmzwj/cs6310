package edu.gatech.cs6310.entity;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item {

    @EmbeddedId
    private final ItemId itemId;

    @Column(name="item_weight")
    private final int itemWeight;

    public Item() {
        this.itemId = null;
        this.itemWeight = 0;
    }
    public Item(ItemId itemId, int itemWeight) {
        this.itemId = itemId;
        this.itemWeight = itemWeight;
    }

    public int getItemWeight() {
        return itemWeight;
    }

    public String displayItem() {
        return this.itemId.getItemName()+","+this.itemWeight;
    }
}
