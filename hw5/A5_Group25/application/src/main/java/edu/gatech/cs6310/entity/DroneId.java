package edu.gatech.cs6310.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DroneId implements Serializable {



    @Column(name="store_name")
    private String storeName;

    @Column(name="identifier")
    private String identifier;

    public DroneId(String storeName, String identifier) {
        this.storeName = storeName;
        this.identifier = identifier;
    }

    public DroneId() {

    }



    public String getIdentifier() {
        return identifier;
    }
}
