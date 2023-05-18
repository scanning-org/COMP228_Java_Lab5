package com.adrianomelquiades_brunomorgado_comp228lab5;

import java.sql.Date;
import javafx.beans.property.*;


public class Player {


    private int id;
    private String first_name;
    private String last_name;
    private String address;
    private String postalCode;
    private String province;
    private long phoneNumber;


    //Default Constructor
    public Player(){}

    //Constructor
    public Player(int id, String first_name, String last_name, String address, String postalCode,
                  String province, long phoneNumber) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.postalCode = postalCode;
        this.province = province;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return id + "\n" +
               first_name + "\n" +
               last_name + "\n" +
               address + "\n" +
               postalCode + "\n" +
               province + "\n";
//               phoneNumber;
    }


}
