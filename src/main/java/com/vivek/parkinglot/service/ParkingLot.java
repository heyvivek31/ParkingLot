package com.vivek.parkinglot.service;

import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.EntryPoint;
import com.vivek.parkinglot.model.Slot;
import com.vivek.parkinglot.exception.ParkingNotAvailableException;

import java.util.List;
import java.util.Map;

public interface ParkingLot extends ParkingService, UnParkService, SlotService {

    public void createParkingLot(int numOfSlots, int entryPoints);
}
