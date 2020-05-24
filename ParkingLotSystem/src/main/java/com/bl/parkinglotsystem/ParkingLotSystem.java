package com.bl.parkinglotsystem;

public class ParkingLotSystem {
    private Object vehicle;

    public ParkingLotSystem() {
    }
    public void park(Object vehicle) throws ParkingLotSystemException {
        if (this.vehicle!=null)
            throw new ParkingLotSystemException("Parking lot is full");
        this.vehicle=vehicle;
    }
    public void unPark(Object vehicle) throws ParkingLotSystemException{
        if (this.vehicle==null)
            throw new ParkingLotSystemException("Parking lot is Empty");
        if( this.vehicle.equals(vehicle))
            this.vehicle = null;
    }

    public boolean isVehicleParked(Object vehicle) {
        if( this.vehicle.equals(vehicle))
            return true;
        return false;
    }
}
