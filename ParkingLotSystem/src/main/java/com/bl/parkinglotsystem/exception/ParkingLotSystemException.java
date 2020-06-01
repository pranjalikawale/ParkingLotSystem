package com.bl.parkinglotsystem.exception;

public class ParkingLotSystemException extends RuntimeException{
    public enum ExceptionType {FULL,EMPTY,ALREADYPARKED,SPACEAVAILABLE,NOSUCHVEHICLEFOUND;}
    public ExceptionType type;

    public ParkingLotSystemException(String message,ExceptionType type) {
        super(message);
        this.type=type;
    }
}

