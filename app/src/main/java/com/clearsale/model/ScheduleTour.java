package com.clearsale.model;

public class ScheduleTour {
    int id;
    String address, date, time;
    
    public ScheduleTour (int id, String address, String date, String time) {
        this.id = id;
        this.address = address;
        this.date = date;
        this.time = time;
    }
    
    public ScheduleTour () {
    }
    
    public int getId () {
        return id;
    }
    
    public void setId (int id) {
        this.id = id;
    }
    
    public String getAddress () {
        return address;
    }
    
    public void setAddress (String address) {
        this.address = address;
    }
    
    public String getDate () {
        return date;
    }
    
    public void setDate (String date) {
        this.date = date;
    }
    
    public String getTime () {
        return time;
    }
    
    public void setTime (String time) {
        this.time = time;
    }
}
