package com.bl.parkinglotsystem;

import com.bl.parkinglotsystem.observer.Observer;

public class AirportSecurity implements Observer {

    private boolean isCapacity;

    @Override
    public boolean IsCapacityFull() {
        return this.isCapacity ;
    }

    @Override
    public boolean IsCapacityEmpty() {
        return this.isCapacity;
    }

    @Override
    public void capacityIsFull() {
        this.isCapacity=true;
    }

    @Override
    public void capacityIsEmpty() {
        this.isCapacity=false;
    }
}
