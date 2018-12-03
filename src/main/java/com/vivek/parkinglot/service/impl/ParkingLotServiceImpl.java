package com.vivek.parkinglot.service.impl;

import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.EntryPoint;
import com.vivek.parkinglot.model.Slot;
import com.vivek.parkinglot.exception.ParkingNotAvailableException;
import com.vivek.parkinglot.service.CacheService;
import com.vivek.parkinglot.service.ParkingLot;

import java.util.*;

public class ParkingLotServiceImpl implements ParkingLot {

    private List<EntryPoint> entryPointList = new ArrayList<EntryPoint>();
    private Map<Slot, Car> slotCarMap;
    private Map<String, List<Car>> colorCarListMap;
    private Map<String, Slot> registrationNumberSlotMap;
    //private Map<EntryPoint, List<Car>> entryPointCarListMap;
    public CacheService cacheService;

    PriorityQueue<Integer> freeSlots = new PriorityQueue<Integer>();

    public ParkingLotServiceImpl()
    {
        slotCarMap = new HashMap<Slot, Car>();
        colorCarListMap = new HashMap<String, List<Car>>();
        registrationNumberSlotMap = new HashMap<String, Slot>();
    }

    public void createParkingLot(int numOfSlots, int entryPoints) {

        for(int i = 1; i <= numOfSlots; i++)
        {
            slotCarMap.put(new Slot(i), null);
            freeSlots.add(i);
        }

        for(int i = 1; i<= entryPoints; i++){
            entryPointList.add(new EntryPoint(i));
        }
        cacheService.setSlotCarMap(slotCarMap);
    }

    public int park(Car car, EntryPoint entryPoint) throws ParkingNotAvailableException
    {
        int slotId = getFreeSlot();
        if(slotId > 0)
        {
            Slot slot = new Slot(slotId);
            slotCarMap.put(slot, car);
            addToColorCarListMap(car);
            addToRegistrationNumberSlotMap(car, slot);
            //addCarToEntryPointList(car, entryPoint);
        }
        else
        {
            throw new ParkingNotAvailableException("Parking Full");
        }
        return slotId;
    }

    public int leave(int slotId)
    {
        Car c = slotCarMap.get(new Slot(slotId));
        slotCarMap.put(new Slot(slotId), null);
        removeFromColorCarListMap(c);
        removeFromRegistrationNumberSlotMap(c);

        freeSlots.add(slotId);
        return slotId;
    }

    private int getFreeSlot()
    {
        if(freeSlots.size() > 0) {
            return freeSlots.poll();
        }
        return -1;
    }

    public Map<Slot, Car> getParkinglotStatus()
    {
        return this.slotCarMap;
    }

    public List<Car> getRegistrationNumbers(String color) {
        return colorCarListMap.get(color);
    }

    public Slot getSlotForCar(String registrationNumber) {
        if(registrationNumberSlotMap.containsKey(registrationNumber))
            return registrationNumberSlotMap.get(registrationNumber);
        else
            return null;
    }

    public List<Slot> getSlots(String color) {
        List<Car> cars = colorCarListMap.get(color);
        List<Slot> slots = new ArrayList<Slot>();
        for(Car c : cars)
        {
            Slot slot = registrationNumberSlotMap.get(c.getRegistrationNumber());
            slots.add(slot);
        }
        return slots;
    }

    private void addToColorCarListMap(Car car)
    {
        String color = car.getColor();
        List<Car> cars = colorCarListMap.get(color);
        if(cars == null)
        {
            cars = new ArrayList<Car>();
            cars.add(car);
            colorCarListMap.put(color, cars);
        }
        else
        {
            cars.add(car);
        }
    }
    private void removeFromColorCarListMap(Car car)
    {
        String color = car.getColor();
        List<Car> cars = colorCarListMap.get(color);
        if(cars != null && cars.size() > 0)
        {
            cars.remove(car);
        }
    }

    private void addToRegistrationNumberSlotMap(Car car, Slot s)
    {
        registrationNumberSlotMap.put(car.getRegistrationNumber(), s);
    }

    private void removeFromRegistrationNumberSlotMap(Car car)
    {
        registrationNumberSlotMap.remove(car.getRegistrationNumber());
    }
}
