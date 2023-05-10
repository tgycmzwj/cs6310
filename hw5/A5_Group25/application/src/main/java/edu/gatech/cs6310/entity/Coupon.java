package edu.gatech.cs6310.entity;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @Column(name = "coupon_Id")
    private String couponId;

    @Column(name = "expiration")
    private LocalDateTime expiration;

    @Column(name = "discount")
    private double discount;

    @Column(name = "state")
    private String state;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "coupons")
    private List<Customer> customers = new ArrayList<>();

    public double getDiscount() {
        return discount;
    }


    public Coupon() {
    }


    public Coupon(String couponId, LocalDateTime expiration, double discount) {
        this.couponId = couponId;
        this.expiration = expiration;
        this.discount = discount;
        this.state = "valid";
    }

    public String getCouponId() {
        return couponId;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setState() {
        this.state = "expired";
    }

    public List<Customer> pickCustomersForCoupon(List<Customer> customersList, Coupon coupon, double baseFrequency, double highFrequency) {
        Random random = new Random();

        List<Customer> pickedCustomers = new ArrayList<>();
        Percentile percentile90 = new Percentile(90);
        double percentile90Value = percentile90.evaluate(customersList.stream().mapToDouble(Customer::getRating).toArray());

        Percentile percentile10 = new Percentile(10);
        double percentile10Value = percentile10.evaluate(customersList.stream().mapToDouble(Customer::getRating).toArray());

        for (Customer customer : customersList) {
            if (customer.getCoupons().contains(coupon)) {
                continue;
            }
            if (customer.getRating() <= percentile10Value || customer.getRating() >= percentile90Value) {
                if (random.nextDouble() <= highFrequency) {
                    pickedCustomers.add(customer);
                }
            } else {
                if (random.nextDouble() <= baseFrequency) {
                    pickedCustomers.add(customer);
                }
            }
        }
        return pickedCustomers;
    }

    public String validityOfCoupon(LocalDateTime systemTime) {
        if (!expiration.isAfter(systemTime)) {
            setState();
            return state;
        }
        return state;
    }

    public static Comparator<Coupon> getComparator() {
        return Comparator.comparing(coupon -> coupon.discount, Comparator.reverseOrder());
    }
}
