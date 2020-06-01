package com.bl.parkinglotsystem.ParkingLot;

import com.bl.parkinglotsystem.attendant.Attendant;
import com.bl.parkinglotsystem.driver.Driver;
import com.bl.parkinglotsystem.exception.ParkingLotSystemException;
import com.bl.parkinglotsystem.model.Vehicle;
import com.bl.parkinglotsystem.observer.Observer;

import java.time.Instant;
import java.util.*;
import java.util.function.LongToDoubleFunction;
import java.util.stream.Collectors;

public class ParkingLot {
    public Map<String,Vehicle> vehicleMap ;
    private Map<Attendant,List<Vehicle>> attendantParkingVehicleInfo;
    private List<Observer> subscriberList;
    public List<String> slotReservedForHandicapDriverList;
    private List<String> emptyList;
    private int capacity;
    private String parkinglotname;
    private char lane;
    private int counter;
    private List<Attendant> attendantList;
    private String vehiclelane;

    public ParkingLot(int capacity,String name) {
        vehicleMap=new HashMap<>();
        attendantParkingVehicleInfo=new HashMap<>();
        attendantList=new ArrayList<>();
        subscriberList=new ArrayList<>();
        emptyList=new ArrayList<>();
        slotReservedForHandicapDriverList=new ArrayList<>();
        this.parkinglotname=name;
        lane='B';
        counter=0;
        setCapacity(capacity);
        setEmptyList();
        initializeSlotReservedForHandicapDriver();
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setEmptyList(){
        char lane='A';
        for(int i=1;i<=capacity;i++){
            for (int j=1;j<=capacity/3;j++)
                emptyList.add(this.parkinglotname+String.valueOf(lane)+String.valueOf((j)));
            lane++;
        }
    }

    public void initializeSlotReservedForHandicapDriver(){
        String lane="A";
        for(int i=1;i<=capacity/3;i++)
            slotReservedForHandicapDriverList.add(this.parkinglotname+lane+String.valueOf(i));
    }

    public int EmptyListSize(){  return emptyList.size();    }

    public void registerSubscriber(Observer observer){
        subscriberList.add(observer);
    }
    public void registerUnsubscriber(Observer observer){
        subscriberList.remove(observer);
    }

    public  void addAttendant(Attendant attendant){
        attendantList.add(attendant);
    }

    public Attendant getAttendant(){
        if(attendantList.size()>1) {
            attendantList.sort(Comparator.comparing(Attendant::getNoOfVehiclePark));
            return attendantList.get(0);
        }
        return attendantList.get(0);
    }

    public void park(Vehicle vehicle, Driver.DriverType driverType) throws ParkingLotSystemException {
        if (vehicleMap.size()==capacity){
            throw new ParkingLotSystemException("Parking lot is full",
                    ParkingLotSystemException.ExceptionType.FULL);
        }
        if (isVehicleParked(vehicle))
            throw new ParkingLotSystemException("Vehicle is already parked",
                    ParkingLotSystemException.ExceptionType.ALREADYPARKED);
        String token=selectSlot(driverType);
        if(emptyList.contains(token)){
            vehicle.setParkedTime();
            vehicle.setLane(vehiclelane);
            vehicle.setLocation(token);
            vehicle.setAttandent(getAttendant().getAttendantName());
            setAttendantVehicalInfo(getAttendant(),vehicle);
            vehicle.setDriverType(driverType);
            vehicleMap.put(token,vehicle);
            emptyList.remove(token);
        }
        if (vehicleMap.size()==capacity){
            for (Observer subscriber:subscriberList)
                subscriber.capacityIsFull();
        }
    }

    public String findParkingSlot(Vehicle vehicle){
        List<String> tokenKey=new ArrayList<>();
        if(vehicleMap.containsValue(vehicle))
        {
            tokenKey=vehicleMap.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), vehicle))
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            return tokenKey.get(0);
        }
        return null;
        //throw new ParkingLotSystemException("No such Vehicle found",ParkingLotSystemException.ExceptionType.NOSUCHVEHICLEFOUND);
    }

    private void setAttendantVehicalInfo(Attendant attendant,Vehicle vehicle) {
        List<Vehicle> temp=new ArrayList<>();
        if(attendantParkingVehicleInfo.containsKey(attendant)){
            temp=attendantParkingVehicleInfo.get(attendant);
        }
        temp.add(vehicle);
        attendantParkingVehicleInfo.put(attendant,temp);
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        if(vehicleMap.containsValue(vehicle))
            return true;
        return false;
    }

    public String selectSlot(Driver.DriverType driverType) {
        if(driverType==Driver.DriverType.HANDICAP)
                return getReservedSlot();
        else if(driverType==Driver.DriverType.NORMAL)
        {
           if(emptyList.size()-slotReservedForHandicapDriverList.size()!=0)
                return getUnreservedSlot();
           else
               return getReservedSlot();
        }
        return null;
    }

    public String getReservedSlot()
    {
        for (String reservedSlot: slotReservedForHandicapDriverList){
            if(emptyList.contains(reservedSlot)){
                slotReservedForHandicapDriverList.remove(reservedSlot);
                vehiclelane=parkinglotname+String.valueOf('A');
                return reservedSlot;
            }
        }

        return null;
    }

    public String getUnreservedSlot(){
        counter++;
        if(counter>capacity/3){
            counter=1;
            lane++;
        }
        vehiclelane=parkinglotname+String.valueOf(lane);
        return parkinglotname+String.valueOf(lane)+String.valueOf(counter);
    }

    public int unPark(Vehicle vehicle) throws ParkingLotSystemException{
        int charges=0;
        if (vehicleMap.size()==0)
            throw new ParkingLotSystemException("Parking lot is Empty",
                    ParkingLotSystemException.ExceptionType.EMPTY);

        if (isVehicleParked(vehicle)){
            if (vehicleMap.size()==capacity){
                for (Observer subscriber:subscriberList)
                    subscriber.capacityIsSpaceAvailable();
                throw new ParkingLotSystemException("Space available in parking lot",
                        ParkingLotSystemException.ExceptionType
                                .SPACEAVAILABLE);
            }
            vehicle.setUnparkedTime();
            charges= vehicle.calculateFare();
            emptyList.add(findParkingSlot(vehicle));
            vehicleMap.remove(vehicle);
        }
        return charges;
    }


    public List<String> getParkingInformationByColor(Vehicle.Color colour) {
        List<String> list=vehicleMap.values().stream()
                .filter(Vehicle->Vehicle.getColor().equals(colour))
                .map(Vehicle->Vehicle.getLocation())
                .collect(Collectors.toList());
        return list;
    }

    public List<Vehicle> getParkingInformationByColorAndModel(Vehicle.Color colour,Vehicle.Model model) {
        List<Vehicle> list=vehicleMap.values().stream()
                .filter(Vehicle->Vehicle.getColor().equals(colour)&&Vehicle.getModel().equals(model))
                .collect(Collectors.toList());
        return list;
    }

    public List<Vehicle> getParkingInformationByModel(Vehicle.Model model) {
        List<Vehicle> list=vehicleMap.values().stream()
                .filter(Vehicle->Vehicle.getModel().equals(model))
                //.map(Vehicle->Vehicle.color.equals(colour))
                .collect(Collectors.toList());
        return list;
    }

   public List<Vehicle> getParkingInformationByTime(Instant time) {
        List<Vehicle> list=vehicleMap.values().stream()
                .filter(Vehicle->Vehicle.getParkedTime().compareTo(time)>0)
                .collect(Collectors.toList());
        return list;
    }

   public List<Vehicle> getParkingInformationByLaneNo(String lane) {
        String laneNo=parkinglotname+lane;
        List<Vehicle> list=vehicleMap.values().stream()
                .filter(Vehicle->Vehicle.getVehicleSize().equals(com.bl.parkinglotsystem.model.Vehicle.Size.SMALL))
                .filter(Vehicle->Vehicle.getLane().equals(laneNo) && Vehicle.getDriverType().equals(Driver.DriverType.HANDICAP))
                .collect(Collectors.toList());
        return list;
    }

    public List<Vehicle> getParkingInformationOfAllVehicle() {
        List<Vehicle> list=vehicleMap.values()
                .stream()
                .collect(Collectors.toList());
        return list;
    }


}
