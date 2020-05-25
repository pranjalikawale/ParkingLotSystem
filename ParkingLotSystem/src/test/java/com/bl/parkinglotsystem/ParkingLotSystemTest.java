package com.bl.parkinglotsystem;

import com.bl.parkinglotsystem.exception.ParkingLotSystemException;
import com.bl.parkinglotsystem.model.AirportSecurity;
import com.bl.parkinglotsystem.model.ParkingLotOwner;
import com.bl.parkinglotsystem.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Object vehicle;

    @Before
    public void setUp(){
        parkingLotSystem=new ParkingLotSystem(2);
        vehicle=new Object();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isParked=parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(e.getMessage(),"Parking lot is full");
        }
    }

    @Test
    public void givenAVehicle_WhenParkingIsFull_ShouldReturnException() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(e.getMessage(),"Parking lot is full");
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(vehicle);
        }catch (ParkingLotSystemException e){
            Assert.assertEquals(e.getMessage(),"Parking lot is Empty");
        }
    }
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturn() {
        try {
            parkingLotSystem.unPark(vehicle);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(e.getMessage(),"Parking lot is Empty");
        }
    }
    @Test
    public void givenParkingLot_WhenParkingLotIsFull_ShouldInformedOwner() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriber(parkingLotOwner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        }
        catch (ParkingLotSystemException e){
            boolean capacityFull=parkingLotOwner.IsCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }
    @Test
    public void givenParkingLot_WhenParkingLotIsFulll_ShouldInformedAirportSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLotSystem.registerSubscriber(airportSecurity);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        }
        catch (ParkingLotSystemException e){
            boolean capacityFull=airportSecurity.IsCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }
    @Test
    public void givenParkingLot_WhenParkingLotIsFulll_ShouldInformedObserver() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLotSystem.registerSubscriber(airportSecurity);
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriber(parkingLotOwner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        }
        catch (ParkingLotSystemException e){
            boolean capacityFull1=airportSecurity.IsCapacityFull();
            boolean capacityFull2=parkingLotOwner.IsCapacityFull();
            Assert.assertTrue(capacityFull1&&capacityFull2);
        }
    }
    @Test
    public void givenParkingLot_WhenParkingSpaceAvailable_ShouldInformedOwner() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriber(parkingLotOwner);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertTrue(capacityEmpty);
        }
    }
}
