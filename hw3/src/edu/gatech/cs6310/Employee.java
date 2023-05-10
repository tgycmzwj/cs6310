package edu.gatech.cs6310;/*
 *@author: lesliezhao
 *@version: 0.1
 */
import java.util.Date;
public class Employee extends User{
    protected String taxID;
    protected Date startDate;
    protected Double curSalary;

    //getter function
    public String getTaxID(){
        return this.taxID;
    }
    public Date getStartDate(){
        return this.startDate;
    }
    public Double getCurSalary(){
        return this.curSalary;
    }

}
