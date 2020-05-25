package com.bl.parkinglotsystem.parkinglotsystem;

import com.bl.parkinglotsystem.exception.ParkingLotSystemException;
import com.bl.parkinglotsystem.model.Vehicle;
import com.bl.parkinglotsystem.observer.Observer;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ParkingLotSystem {
    private HashMap<Integer,Vehicle> vehicleMap ;
    private List<Observer> subscriberList;
    private List<Integer> emptyList;
    private int capacity;
    Random random;
    int token;
    private boolean flag=true;

    public ParkingLotSystem(int capacity) {
        vehicleMap=new HashMap<>();
        subscriberList=new ArrayList<>();
        emptyList=new ArrayList<>();
        random=new Random();
        this.capacity=capacity;
    }

    public void registerSubscriber(Observer observer){
        subscriberList.add(observer);
    }

    public void registerUnsubscriber(Observer observer){
        subscriberList.remove(observer);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        setEmptyList();
    }
    public void setEmptyList(){
       for(int i=0;i<capacity;i++)
           emptyList.add(i);
    }
    /*
    public List<Integer> getEmptyList() {
        return emptyList;
    }*/

    public int getEmptyListSize() {
        return 1+emptyList.size();
    }

    public int park(Vehicle vehicle) throws ParkingLotSystemException {
        if (vehicleMap.size()==capacity){
            for (Observer subscriber:subscriberList) {
                subscriber.capacityIsFull();
            }
            throw new ParkingLotSystemException("Parking lot is full",
                                                ParkingLotSystemException.ExceptionType.FULL);
        }
        if (isVehicleParked(vehicle))
            throw new ParkingLotSystemException("Vehicle is already parked",
                                                ParkingLotSystemException.ExceptionType.ALREADYPARKED);

        while(flag){
            token=selectRandomSlot();
            if(vehicleMap.get(token) == null){
                vehicle.setParkedTime();
                vehicleMap.put(token,vehicle);
                if(emptyList.contains(token))
                    emptyList.remove(token);
                break;
            }
        }
        return token;
    }

    public int unPark(int token) throws ParkingLotSystemException{
        int charges=0;
        if (token<0)
            throw new ParkingLotSystemException("Parking lot is Empty",
                                                ParkingLotSystemException.ExceptionType.EMPTY);

        if (isVehicleParked(token)){
            if (vehicleMap.size()==capacity){
                for (Observer subscriber:subscriberList)
                    subscriber.capacityIsSpaceAvailable();
                throw new ParkingLotSystemException("Space available in parking lot",
                                                     ParkingLotSystemException.ExceptionType
                                                     .SPACEAVAILABLE);
            }
            Vehicle vehicle=vehicleMap.get(token);
            vehicle.setUnparkedTime();
             charges= vehicle.calculateFare();
            vehicleMap.remove(token);
            if(emptyList.contains(token))
                emptyList.add(token);
        }
        return charges;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        if(vehicleMap.containsValue(vehicle))
            return true;
        return false;
    }

    public boolean isVehicleParked(int token){
        if(vehicleMap.containsKey(token))
            return true;
        return false;
    }

    public int selectRandomSlot(){
        int  ranVal=random.nextInt(capacity);
        return ranVal;
    }

}
