package edu.gatech.cs6310.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pilot")
public class Pilot extends User {

    @Column(name="tax_id")
    private final String taxId;
    @Column(name="license_id", unique = true)
    private final String licenseId;
    @Column(name="experience_level")
    private int experienceLevel;
    @Column(name="drone_id")
    private String droneId;

    @Column(name="store_name")
    private String storeName;

    public Pilot() {
        this.taxId = "";
        this.licenseId = "";
    }
    public Pilot(String account, String firstName, String lastName, String phoneNumber, String taxId, String licenseId, int experienceLevel) {
        super(account, firstName, lastName, phoneNumber);
        this.taxId = taxId;
        this.licenseId = licenseId;
        this.experienceLevel = experienceLevel;
    }

    public String getDroneId() {
        return droneId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void updateExperienceLevel(){
        this.experienceLevel+=1;
    }

    public void onDuty(String storeName, String droneId){
        this.droneId = droneId;
        this.storeName = storeName;
    }

    public void offDuty(){
        this.droneId = null;
        this.storeName = null;
    }

    public String displayPilot(){
        return "name:"+getFirstName()+"_"+getLastName()+",phone:"+getPhoneNumber()+",taxID:"+this.taxId+",licenseID:"+this.licenseId+",experience:"+this.experienceLevel;
    }

    public String getName(){
        return getFirstName()+"_"+getLastName();
    }
}
