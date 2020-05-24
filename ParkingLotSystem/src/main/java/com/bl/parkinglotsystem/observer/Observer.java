package com.bl.parkinglotsystem.observer;

public interface Observer {
    boolean IsCapacityFull();
    boolean IsCapacityEmpty();
    void capacityIsFull();
    void capacityIsEmpty();

}
