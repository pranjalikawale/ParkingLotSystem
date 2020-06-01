package com.bl.parkinglotsystem;

import com.bl.parkinglotsystem.attendant.Attendant;
import com.bl.parkinglotsystem.driver.Driver;
import com.bl.parkinglotsystem.exception.ParkingLotSystemException;
import com.bl.parkinglotsystem.model.AirportSecurity;
import com.bl.parkinglotsystem.model.ParkingLotOwner;
import com.bl.parkinglotsystem.model.Vehicle;
import com.bl.parkinglotsystem.observer.Observer;
import com.bl.parkinglotsystem.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Vehicle vehicle;

    @Before
    public void setUp(){
        parkingLotSystem=new ParkingLotSystem(3);
        vehicle=new Vehicle();
        parkingLotSystem.addParkingLot(1);
        parkingLotSystem.registerAttendant(new Attendant[]{new Attendant("def"),new Attendant("xyz")});
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            boolean isParked=parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.FULL,e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnException() {
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.ALREADYPARKED,e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenParkingIsFull_ShouldReturnException() {
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.FULL,e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.findParkingSlot(vehicle);
        }catch (ParkingLotSystemException e){
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.SPACEAVAILABLE,e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnException() {
        try {
            String slot=parkingLotSystem.findParkingSlot(vehicle);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.NOSUCHVEHICLEFOUND,e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenParkingLotIsFull_ShouldInformedOwner() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{parkingLotOwner});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            boolean capacityFull=parkingLotOwner.IsCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }
    @Test
    public void givenAVehicle_WhenParkingLotIsFulll_ShouldInformedAirportSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{airportSecurity});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            boolean capacityFull=airportSecurity.IsCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }
    @Test
    public void givenAVehicle_WhenParkingLotIsFulll_ShouldInformedObserver() {
        AirportSecurity airportSecurity=new AirportSecurity();
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{airportSecurity,parkingLotOwner});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            boolean capacityFull1=airportSecurity.IsCapacityFull();
            boolean capacityFull2=parkingLotOwner.IsCapacityFull();
            Assert.assertTrue(capacityFull1&&capacityFull2);
        }
    }
    @Test
    public void givenAVehicle_WhenParkingSpaceAvailable_ShouldInformedOwner() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{parkingLotOwner});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.unParked(vehicle);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty);
        }
    }
    @Test
    public void givenAVehicle_WhenParkingSpaceAvailable_ShouldInformedAirportSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{airportSecurity});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.unParked(vehicle);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty=airportSecurity.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty);
        }
    }
    @Test
    public void givenAVehicle_WhenParkingSpaceAvailable_ShouldInformedObserver() {
        AirportSecurity airportSecurity=new AirportSecurity();
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{airportSecurity,parkingLotOwner});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.unParked(vehicle);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty1=airportSecurity.IsCapacitySpaceAvailable();
            boolean capacityEmpty2=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty1&&capacityEmpty2);
        }
    }
    @Test
    public void givenSubscriber_WhenSubscriberIsRemoved_ShouldOnlyInformedRemainingObserver() {
        AirportSecurity airportSecurity=new AirportSecurity();
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{parkingLotOwner,airportSecurity});
        parkingLotSystem.removedSubscriber(new Observer[]{airportSecurity});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.unParked(vehicle);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty1=airportSecurity.IsCapacitySpaceAvailable();
            boolean capacityEmpty2=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty1&&capacityEmpty2);
        }
    }
    @Test
    public void givenAVehicle_WhenParkingSpaceAvailable_ShouldReturnTotalEmptyParkingSlot() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{parkingLotOwner});
        //parkingLotSystem.setCapacity(3);
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            parkingLotSystem.unParked(vehicle);
            int[] slot=parkingLotSystem.getEmptyListSize();
            Assert.assertEquals(2,slot[0]);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty);
        }
    }
    @Test
    public void givenAParkinglot_WhenFindVehicle_ShouldReturnParkingSlot() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{parkingLotOwner});
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            String slot=parkingLotSystem.findParkingSlot(vehicle);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            Assert.assertEquals("Level-1-B1",slot);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty);
        }
    }
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnCharges() {
        ParkingLotOwner parkingLotOwner=new ParkingLotOwner();
        parkingLotSystem.registerSubscriberPLS(new Observer[]{parkingLotOwner});
        //parkingLotSystem.setCapacity(3);
        try {
            parkingLotSystem.park(vehicle,Driver.DriverType.NORMAL);
            parkingLotSystem.park(new Vehicle(),Driver.DriverType.NORMAL);
            int charges=parkingLotSystem.unParked(vehicle);
            Assert.assertEquals(100,charges);
        }
        catch (ParkingLotSystemException e){
            boolean capacityEmpty=parkingLotOwner.IsCapacitySpaceAvailable();
            Assert.assertFalse(capacityEmpty);
        }
    }
    @Test
    public void givenVehicle_WhenEvenlyParkedInParkinglot_ShouldReturnTrue() {
        //Set number of parkingLot
        parkingLotSystem.addParkingLot(1);
        parkingLotSystem.registerAttendant(new Attendant[]{new Attendant("def"),new Attendant("xyz")});
        parkingLotSystem.registerSubscriberPLS(new Observer[]{new ParkingLotOwner(),
                                                                new ParkingLotOwner()});
        parkingLotSystem.park(vehicle, Driver.DriverType.NORMAL);
        Vehicle vehicle1=new Vehicle();
        parkingLotSystem.park(vehicle1, Driver.DriverType.NORMAL);
        Assert.assertEquals("Level-1-B1",parkingLotSystem.findParkingSlot(vehicle));
        Assert.assertEquals("Level-2-B1",parkingLotSystem.findParkingSlot(vehicle1));
    }
    @Test
    public void givenVehicle_WhenDriverIsHandicap_ShouldReturnNearParkingSlot() {
        //Set number of parkingLot
        parkingLotSystem.addParkingLot(1);
        parkingLotSystem.registerSubscriberPLS(new Observer[]{new ParkingLotOwner()
                , new ParkingLotOwner()});
        parkingLotSystem.park(vehicle, Driver.DriverType.HANDICAP);
        Assert.assertEquals("Level-1-A1",parkingLotSystem.findParkingSlot(vehicle));
    }
    @Test
    public void givenLargeVehicle_WhenParkingHavingMoreSpace_ShouldAllocateFreeSpaceParkinglot() {
        //Set number of parkingLot
        parkingLotSystem.addParkingLot(1);
        parkingLotSystem.registerAttendant(new Attendant[]{new Attendant("pqr"),new Attendant("std")});
        parkingLotSystem.registerSubscriberPLS(new Observer[]{new ParkingLotOwner(), new ParkingLotOwner()});
        parkingLotSystem.park(new Vehicle("MH123", Vehicle.Color.WHITE,Vehicle.Size.LARGE), Driver.DriverType.NORMAL);
        parkingLotSystem.park(new Vehicle("MH223", Vehicle.Color.WHITE,Vehicle.Size.SMALL), Driver.DriverType.NORMAL);
        parkingLotSystem.park(new Vehicle("MH323", Vehicle.Color.WHITE,Vehicle.Size.LARGE), Driver.DriverType.HANDICAP);
        parkingLotSystem.park(new Vehicle("MH243", Vehicle.Color.WHITE, Vehicle.Size.SMALL), Driver.DriverType.NORMAL);
        Vehicle vehicle1=new Vehicle("MH243", Vehicle.Color.WHITE, Vehicle.Size.LARGE);
        parkingLotSystem.park(vehicle1,Driver.DriverType.NORMAL);
        Assert.assertEquals("Level-1-A1",parkingLotSystem.findParkingSlot(vehicle1));
    }

}
