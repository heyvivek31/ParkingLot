package com.vivek.parkinglot.service;

import com.vivek.parkinglot.model.Slot;

import java.util.List;

public interface SlotService {
    public Slot getSlotForCar(String registrationNumber);

    public List<Slot> getSlots(String color);
}
