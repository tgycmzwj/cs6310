package edu.gatech.cs6310.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customer")
public class Customer extends User implements Comparable<Customer> {

    @Column(name = "rating")
    private int rating;

    @Column(name = "credit")
    private int credit;

    @Column(name = "total_order_cost")
    private int totalOrderCost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_account"),
            inverseJoinColumns = @JoinColumn(name = "coupon_Id")
    )
    private List<Coupon> coupons = new ArrayList<>();

    public Customer(String account, String firstName, String lastName, String phoneNumber, int rating, int credits) {
        super(account, firstName, lastName, phoneNumber);
        this.rating = rating;
        this.credit = credits;
        this.totalOrderCost = 0;
    }

    public Customer() {

    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void updateTotalOrderCost(int cost) {
        this.totalOrderCost += cost;
    }

    private void updateCredit(int cost) {
        this.credit += cost;
    }

    public boolean checkCredit(int cost) {
        return this.totalOrderCost + cost <= this.credit;
    }

    public void orderCompleted(int cost) {
        updateTotalOrderCost(-cost);
        updateCredit(-cost);
    }

    public String displayCustomer() {
        return "name:" + getFirstName() + "_" + getLastName() + ",phone:" + getPhoneNumber() + ",rating:" + this.rating + ",credit:" + this.credit;
    }

    public int compareTo(Customer o) {
        return Integer.compare(this.rating, o.rating);
    }

    public void refund(int value) {
        credit += value;
    }

    /**
     * Remove expired coupons, sort coupons based on discount value
     */
    public void sortAndSelectValidCoupons(LocalDateTime systemTime) {
        coupons.removeIf(coupon -> coupon.validityOfCoupon(systemTime).equals("expired"));
        coupons.sort(Coupon.getComparator());
    }

    public int getRating() {
        return rating;
    }
}
