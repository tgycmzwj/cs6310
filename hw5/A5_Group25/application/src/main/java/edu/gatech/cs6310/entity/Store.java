package edu.gatech.cs6310.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "store")
public class Store {

    @Id
    @Column(name = "store_name")
    private final String storeName;

    @Column(name = "revenue")
    private int revenue;

    @Column(name = "purchases")
    private int purchases;

    @Column(name = "overloads")
    private int overloads;

    @Column(name = "transfers")
    private int transfers;

    @Column(name = "location_x")
    private int locationX;

    @Column(name = "location_y")
    private int locationY;

    public Store() {
        this.storeName = "";
    }

    public Store(String storeName, int revenue) {
        this.storeName = storeName;
        this.revenue = revenue;
        this.purchases = 0;
        this.overloads = 0;
        this.transfers = 0;
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


    public void updateRevenue(int payment) {
        this.revenue += payment;
    }

    public void updatePurchases() {
        this.purchases += 1;
    }

    public void updateOverloads(int unpaidOrders) {
        this.overloads += unpaidOrders;
    }

    public void updateTransfers() {
        this.transfers += 1;
    }

    public void orderPaid(int payment, int unpaidOrders) {
        updateRevenue(payment);
        updatePurchases();
        updateOverloads(unpaidOrders);
    }

    public String displayStore() {
        return "name:" + this.storeName + ",revenue:" + this.revenue;
    }

    public String displayEfficiency() {
        return "name:" + this.storeName + ",purchases:" + this.purchases + ",overloads:" + this.overloads + ",transfers:" + this.transfers;
    }
}
