package com.vivek.parkinglot.service;

import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.Slot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CacheService {
    private static Map<Slot, Car> slotCarMap = new HashMap<Slot, Car>();
    private static Map<String, List<Car>> colorCarListMap = new HashMap<String, List<Car>>();
    private static Map<String, Slot> registrationNumberSlotMap = new HashMap<String, Slot>();

    public static Map<Slot, Car> getSlotCarMap() {
        return slotCarMap;
    }

    public static void setSlotCarMap(Map<Slot, Car> slotCarMap) {
        slotCarMap = slotCarMap;
    }

    public static Map<String, List<Car>> getColorCarListMap() {
        return colorCarListMap;
    }

    public static void setColorCarListMap(Map<String, List<Car>> colorCarListMap) {
        colorCarListMap = colorCarListMap;
    }

    public static Map<String, Slot> getRegistrationNumberSlotMap() {
        return registrationNumberSlotMap;
    }

    public static void setRegistrationNumberSlotMap(Map<String, Slot> registrationNumberSlotMap) {
        registrationNumberSlotMap = registrationNumberSlotMap;
    }
}

