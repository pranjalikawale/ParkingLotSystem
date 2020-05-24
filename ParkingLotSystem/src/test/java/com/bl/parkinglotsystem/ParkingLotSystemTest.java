package com.bl.parkinglotsystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Object vehicle;

    @Before
    public void setUp(){
        parkingLotSystem=new ParkingLotSystem();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(vehicle);
        }catch (ParkingLotSystemException e){
            Assert.assertEquals(e.getMessage(),"Parking lot is Empty");
            e.printStackTrace();
        }
    }
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturn() {
        try {
            parkingLotSystem.unPark(vehicle);
        }
        catch (ParkingLotSystemException e){
            Assert.assertEquals(e.getMessage(),"Parking lot is Empty");
            e.printStackTrace();
        }
    }
}
