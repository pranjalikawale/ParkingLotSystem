package com.bl.parkinglotsystem;

public class ParkingLotSystem {
    private Object vehicle;

    public ParkingLotSystem() {
    }
    public boolean park(Object vehicle){
        this.vehicle=vehicle;
        return true;
    }
}
