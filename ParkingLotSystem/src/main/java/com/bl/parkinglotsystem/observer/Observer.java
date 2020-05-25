package com.bl.parkinglotsystem.observer;

public interface Observer {
    boolean IsCapacityFull();
    boolean IsCapacitySpaceAvailable();
    void capacityIsFull();
    void capacityIsSpaceAvailable();

}
