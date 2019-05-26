package com.aditya.zerocarbon;

public class Ride {
    private String id;
    private String from;
    private String to;
    private int seats;

    public Ride() {

    }

    public Ride(String id, String f , String t, int s) {
        this.id = id;
        this.from =f;
        this.to =t;
        this.seats = s;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getSeats() {
        return seats;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ride from '"+from+"' to '"+to+"' with "+seats+" seats";
    }
}
