package com.example.emergencyapp;

import android.widget.Button;

public class user {
    String fName, fdistrict, fSubDistrict, email, Mobile, status, hospital;
    Button call;

    public user() {
    }


    public user(String fName, String fdistrict, String fSubDistrict, String email, String mobile, Button call, String status, String hospital) {
        this.fName = fName;
        this.fdistrict = fdistrict;
        this.fSubDistrict = fSubDistrict;
        this.email = email;
        Mobile = mobile;
        this.call = call;
        this.status = status;
        this.hospital=hospital;

    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Button getCall() {
        return call;
    }

    public void setCall(Button call) {
        this.call = call;
    }

    //new

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getFdistrict() {
        return fdistrict;
    }

    public void setFdistrict(String fdistrict) {
        this.fdistrict = fdistrict;
    }

    public String getfSubDistrict() {
        return fSubDistrict;
    }

    public void setfSubDistrict(String fSubDistrict) {
        this.fSubDistrict = fSubDistrict;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
