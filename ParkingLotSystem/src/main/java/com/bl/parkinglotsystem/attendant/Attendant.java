package com.bl.parkinglotsystem.attendant;

public class Attendant {
    private String attendantName;
    private int parkingCounter;
    public Attendant() {
        this.parkingCounter=0;
        //this.attendantName=attendantName;
    }
    public Attendant(String attendantName) {
        this.parkingCounter=0;
        this.attendantName=attendantName;
    }

    public void setAttendantName(String attendantName){
        this.attendantName=attendantName;
    }

    public String getAttendantName() {
        return attendantName;
    }


    public int getNoOfVehiclePark() {
        return parkingCounter++;
    }
}