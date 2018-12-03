package com.vivek.parkinglot.factory;

import com.vivek.parkinglot.service.ParkingLot;
import com.vivek.parkinglot.service.impl.ParkingLotServiceImpl;

public class ParkingLotFactory {

    public static ParkingLot createParkingLot()
    {
        return new ParkingLotServiceImpl();
    }
}
