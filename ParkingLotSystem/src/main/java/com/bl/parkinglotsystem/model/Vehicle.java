package com.bl.parkinglotsystem.model;

import java.time.Duration;
import java.time.Instant;

public class Vehicle {
    Instant parkedTime,unparkedTime;
    private static final int CHARGES = 100;

    public Instant getParkedTime() {
        return parkedTime;
    }

    public void setParkedTime() {
        this.parkedTime=Instant.now();
    }

    public Instant getUnparkedTime() {
        return unparkedTime;
    }

    public void setUnparkedTime() {
        this.unparkedTime=Instant.now();
    }
    public int calculateFare(){
        int charges=CHARGES;
        Duration timeElapsed = Duration.between(this.parkedTime, this.unparkedTime);
        long hour=timeElapsed.toHours();
        for (int i=1;i<=hour;i++){
            charges = charges*i;
        }
        return charges;
    }

}
