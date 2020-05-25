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
            throw new ParkingLotSystemException("Parking lot is full",
                                                ParkingLotSystemException.ExceptionType.FULL);
        }
        if (isVehicleParked(vehicle))
            throw new ParkingLotSystemException("Vehicle is already parked",
                                                ParkingLotSystemException.ExceptionType.ALREADYPARKED);
        vehicleList.add(vehicle);
    }

    public void unPark(Object vehicle) throws ParkingLotSystemException{
        if (vehicle==null)
            throw new ParkingLotSystemException("Parking lot is Empty",
                                                ParkingLotSystemException.ExceptionType.EMPTY);

        if (isVehicleParked(vehicle)){
            if (vehicleList.size()==capacity){
                for (Observer subscriber:subscriberList)
                    subscriber.capacityIsSpaceAvailable();
                throw new ParkingLotSystemException("Space Available in parking lot",
                                                    ParkingLotSystemException.ExceptionType.SPACEAVAILABLE);
            }
            vehicleList.remove(vehicle);
        }
    }

    public boolean isVehicleParked(Object vehicle) {
        if(vehicleList.contains(vehicle))
            return true;
        return false;
    }
}
