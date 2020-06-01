package com.bl.parkinglotsystem.model;

import com.bl.parkinglotsystem.driver.Driver;

import java.time.Duration;
import java.time.Instant;

public class Vehicle {
    Instant parkedTime,unparkedTime;
    private static final int CHARGES = 100;
    private Model vehicleModel;
    private String numberPlate;
    private Color color;
    private Size vehicleSize;
    private String location;
    private String attendantName;
    private Driver.DriverType  driverType;
    private String lane;

    public enum Color{BLUE,WHITE;}
    public enum Size{SMALL,LARGE;}
    public enum Model{BMW,TOYOTO}

    public Vehicle(){}

    public Vehicle(String numberPlate,Color color,Model model){
        this.numberPlate=numberPlate;
        this.color=color;
        this.vehicleModel=model;
    }

    public Vehicle(String numberPlate,Color color,Size vehicleSize){
        this.numberPlate=numberPlate;
        this.color=color;
        this.vehicleSize=vehicleSize;
    }

    public Vehicle(String numberPlate, Color color, Model model, Size vehicleSize){
        this.numberPlate=numberPlate;
        this.color=color;
        this.vehicleModel=model;
        this.vehicleSize=vehicleSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Color getColor() {
        return color;
    }

    public Model getModel() {
        return vehicleModel;
    }

    public Size getVehicleSize() {
        return vehicleSize;
    }

    public Instant getParkedTime() {
        return parkedTime;
    }

    public void setAttandent(String attendantName) {
        this.attendantName=attendantName;
    }

    public Driver.DriverType getDriverType() {
        return driverType;
    }

    public void setDriverType(Driver.DriverType driverType) {
        this.driverType=driverType;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public void setParkedTime() {
        this.parkedTime=Instant.now();
    }

    public void setUnparkedTime() {
        this.unparkedTime=Instant.now();
    }

    public int calculateFare(){
        int charges=CHARGES;
        Duration timeElapsed = Duration.between(this.parkedTime, this.unparkedTime);
        int hour=(int)timeElapsed.toHours();
        if(hour>0)
            return charges*hour;
        return charges;
    }

}
