package com.bl.parkinglotsystem.parkinglotsystem;

import com.bl.parkinglotsystem.exception.ParkingLotSystemException;
import com.bl.parkinglotsystem.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private List<Object> vehicleList;
    private List<Observer> subscriberList;
    private int capacity;

    public ParkingLotSystem(int capacity) {
        vehicleList=new ArrayList<>();
        subscriberList=new ArrayList<>();
        this.capacity=capacity;
    }

    public void registerSubscriber(Observer observer){
        subscriberList.add(observer);
    }

    public void park(Object vehicle) throws ParkingLotSystemException {
        if (vehicleList.size()==capacity){
            for (Observer subscriber:subscriberList) {
                subscriber.capacityIsFull();
            }
            throw new ParkingLotSystemException("Parking lot is full");
        }
        if (isVehicleParked(vehicle))
            throw new ParkingLotSystemException("Vehicle is already parked");
        vehicleList.add(vehicle);
    }

    public void unPark(Object vehicle) throws ParkingLotSystemException{
        if (vehicle==null){
            throw new ParkingLotSystemException("Parking lot is Empty");
        }

        if (isVehicleParked(vehicle)){
            vehicleList.remove(vehicle);
            if (vehicleList.size()<capacity){
                for (Observer subscriber:subscriberList) {
                    subscriber.capacityIsSpaceAvailable();
                }
                throw new ParkingLotSystemException("Space Available in parking lot");
            }
        }

    }

    public boolean isVehicleParked(Object vehicle) {
        if(vehicleList.contains(vehicle))
            return true;
        return false;
    }
}
