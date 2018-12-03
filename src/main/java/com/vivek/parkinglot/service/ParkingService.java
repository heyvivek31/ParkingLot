package com.vivek.parkinglot.service;

import com.vivek.parkinglot.exception.ParkingNotAvailableException;
import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.EntryPoint;
import com.vivek.parkinglot.model.Slot;

import java.util.List;
import java.util.Map;

public interface ParkingService {
    public int park(Car car, EntryPoint entryPoint) throws ParkingNotAvailableException;

    public Map<Slot, Car> getParkinglotStatus();

    public List<Car> getRegistrationNumbers(String color);
}
