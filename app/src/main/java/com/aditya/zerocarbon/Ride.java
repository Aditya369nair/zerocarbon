package com.aditya.zerocarbon;

public class Ride {
    private String from;
    private String to;
    private String seats;

    public Ride() {

    }

    public Ride(String f , String t, String s) {
        this.from =f;
        this.to =t;
        this.seats = s;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Ride from '"+from+"' to '"+to+"' with "+seats+" seats";
    }
}
