package com.bl.parkinglotsystem.model;

import com.bl.parkinglotsystem.observer.Observer;

public class AirportSecurity implements Observer {

    private boolean isCapacityFull;

    @Override
    public boolean IsCapacityFull() {
        return this.isCapacityFull ;
    }

    @Override
    public boolean IsCapacitySpaceAvailable() {
        return this.isCapacityFull;
    }

    @Override
    public void capacityIsFull() {
        this.isCapacityFull=true;
    }

    @Override
    public void capacityIsSpaceAvailable() {
        this.isCapacityFull=false;
    }
}
