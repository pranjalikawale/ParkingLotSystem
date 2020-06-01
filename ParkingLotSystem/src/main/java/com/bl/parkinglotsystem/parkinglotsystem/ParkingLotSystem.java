/*********************************************************************
 * @purpose: Parking Lot System
 * @author: Pranjali Kawale
 * @date: 24-05-20
 *********************************************************************/
package com.bl.parkinglotsystem.parkinglotsystem;

import com.bl.parkinglotsystem.ParkingLot.ParkingLot;
import com.bl.parkinglotsystem.attendant.Attendant;
import com.bl.parkinglotsystem.driver.Driver;
import com.bl.parkinglotsystem.exception.ParkingLotSystemException;
import com.bl.parkinglotsystem.model.Vehicle;
import com.bl.parkinglotsystem.observer.Observer;

import java.time.Instant;
import java.util.*;

public class ParkingLotSystem {
    //Variable
    private int capacity;
    private List<ParkingLot> parkingLotList;

    //Constructor
   public ParkingLotSystem(int capacity) {
        parkingLotList=new ArrayList<>();
        this.capacity=capacity;
    }

    /**
     * @purpose: No of parking lot
     * @param parkingLotSize
     */
    public void addParkingLot(int parkingLotSize){
       int size=parkingLotList.size()+parkingLotSize;
        for (int i=parkingLotList.size();i<size;i++)
            parkingLotList.add(new ParkingLot(capacity,"Level-"+size+"-"));
    }

    /**
     * @purpose: Get Information By Vehicle Color from parkinglot
     * @param color
     * @return
     */
    public Map<Integer,List<String>> getInformationByVehicleColor(Vehicle.Color color) {
      Map<Integer,List<String>> parkingInformationDetail=new HashMap<>();
        for (int i=0;i<parkingLotList.size();i++)
            parkingInformationDetail.put(i,parkingLotList.get(i).getParkingInformationByColor(color));
        return parkingInformationDetail;
    }

    /**
     * @purpose: Get Information By Vehicle Color And Model from parkinglot
     * @param color
     * @param model
     * @return parkingInformationDetailMap
     */
    public Map<Integer,List<Vehicle>> getInformationByVehicleColorAndModel(Vehicle.Color color,
                                                                                  Vehicle.Model model) {
        Map<Integer,List<Vehicle>> parkingInformationDetailMap=new HashMap<>();
        for (int i=0;i<parkingLotList.size();i++)
            parkingInformationDetailMap.put(i,parkingLotList.get(i).getParkingInformationByColorAndModel(color,model));
        return parkingInformationDetailMap;
    }

    /**
     * @purpose: Get Information By Vehicle Model from parkinglot
     * @param model
     * @return parkingInformationDetailMap
     */
    public Map<Integer,List<Vehicle>> getInformationByVehicleModel(Vehicle.Model model) {
        Map<Integer,List<Vehicle>> parkingInformationDetailMap=new HashMap<>();
        for (int i=0;i<parkingLotList.size();i++)
            parkingInformationDetailMap.put(i,parkingLotList.get(i).getParkingInformationByModel(model));
        return parkingInformationDetailMap;
    }

    /***
     * @purpose: Get Information By time from parkinglot
     * @param time
     * @return parkingInformationDetailMap
     */
    public Map<Integer,List<Vehicle>> getInformationByTime(Instant time) {
        Map<Integer,List<Vehicle>> parkingInformationDetailMap=new HashMap<>();
        for (int i=0;i<parkingLotList.size();i++)
            parkingInformationDetailMap.put(i,parkingLotList.get(i).getParkingInformationByTime(time));
        return parkingInformationDetailMap;
    }

    /***
     * @purpose: Get Information By Lane from parkinglot
     * @param lane
     * @return parkingInformationDetailMap
     */
    public Map<Integer,List<Vehicle>> getInformationByLaneWise(String lane) {
        Map<Integer,List<Vehicle>> parkingInformationDetailMap=new HashMap<>();
        for (int i=0;i<parkingLotList.size();i++)
            parkingInformationDetailMap.put(i,parkingLotList.get(i).getParkingInformationByLaneNo(lane));
        return parkingInformationDetailMap;
    }

    /***
     * @purpose: Get Information Of All Vehicle from parkinglot
     * @return parkingInformationDetailMap
     */
    public Map<Integer,List<Vehicle>> getInformationOfAllCar() {
        Map<Integer,List<Vehicle>> parkingInformationDetailMap=new HashMap<>();
        for (int i=0;i<parkingLotList.size();i++)
            parkingInformationDetailMap.put(i,parkingLotList.get(i).getParkingInformationOfAllVehicle());
        return parkingInformationDetailMap;
    }

    /***
     * @purpose: To park vehicle in parkinglot
     * @param vehicle
     * @param driverType
     * @throws ParkingLotSystemException
     */
    public void park(Vehicle vehicle, Driver.DriverType driverType) throws ParkingLotSystemException {
       //ParkingLot EmptySlots
        if(parkingLotList.size()>1) {
            parkingLotList.sort(Comparator.comparing(ParkingLot::EmptyListSize).reversed());
            parkingLotList.get(0).park(vehicle,driverType);
            return;
        }
         parkingLotList.get(0).park(vehicle,driverType);
    }

    /***
     * @purpose: To check vehicle is park or not
     * @param vehicle
     * @return
     */
    public boolean isVehicleParked(Vehicle vehicle){
        boolean flag=false;
        for (int i=0;i<parkingLotList.size();i++)
            flag=parkingLotList.get(i).isVehicleParked(vehicle);
        return flag;
    }

    /***
     * @purpose: To get empty slot list
     * @return emptySlotList
     */
    public int[] getEmptyListSize(){
        int[] emptySlotList=new int[parkingLotList.size()];
        for (int i=0;i<parkingLotList.size();i++)
            emptySlotList[i]=parkingLotList.get(i).EmptyListSize();
        return emptySlotList;
    }

    /***+
     * @purpose: To unpark a vehicle from parking lot
     * @param vehicle
     * @return charges
     */
    public int unParked(Vehicle vehicle){
       int charges=0;
       for (int i=0;i<parkingLotList.size();i++){
           charges=parkingLotList.get(i).unPark(vehicle);
           if(charges!=0)
               break;
       }
        return charges;
    }

    /***
     * @purpose To find parking slot of vehicle
     * @param vehicle
     * @return slot
     */
    public String findParkingSlot(Vehicle vehicle){
        for (int i=0;i<parkingLotList.size();i++){
           String slot=parkingLotList.get(i).findParkingSlot(vehicle);
            if(slot!=null)
                return slot;
        }
        throw new ParkingLotSystemException("No such Vehicle found",ParkingLotSystemException.ExceptionType.NOSUCHVEHICLEFOUND);
    }

    /***
     * @purpose To Register Subscriber of parkinglot
     * @param observer
     */
    public void registerSubscriberPLS(Observer[] observer){
        for (int i=0;i<parkingLotList.size();i++) {
            parkingLotList.get(i).registerSubscriber(observer[i]);
        }
    }

    /***
     * @purpose To remove Subscriber of parkinglot
     * @param observer
     */
    public void removedSubscriber(Observer[] observer){
        for (int i=0;i<parkingLotList.size();i++) {
            parkingLotList.get(i).registerUnsubscriber(observer[i]);
        }
    }

    /***
     * @purpose To register attendant of parkingslot
     * @param attendants
     */
    public  void registerAttendant(Attendant[] attendants){
        for (int i=0,j=0;i<attendants.length;i++) {
            parkingLotList.get(j).addAttendant(attendants[i]);
            if(j<parkingLotList.size()-1)
                j++;
        }
    }

}
