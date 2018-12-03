package com.vivek.parkinglot;

import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.EntryPoint;
import com.vivek.parkinglot.model.Slot;
import com.vivek.parkinglot.exception.ParkingNotAvailableException;
import com.vivek.parkinglot.service.ParkingLot;
import com.vivek.parkinglot.service.impl.ParkingLotServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class ParkingLotIntegrationTest {

    static ParkingLot parkintLot;
    static int numSlots = 6;
    static int entryPoint = 3;

    @BeforeClass
    public static void setup()
    {
        parkintLot = new ParkingLotServiceImpl();
    }


    @Test
    public void park() throws Exception
    {
        parkintLot.createParkingLot(numSlots, entryPoint);
        System.out.println("Created a parking lot with " + numSlots + " slots");

        int slotId = parkintLot.park(new Car("KA-01-HH-1234", "White"), new EntryPoint(1));
        assertEquals(1, slotId);
        System.out.println("Allocated slot number:" + slotId);

        slotId = parkintLot.park(new Car("KA-01-HH-9999", "White"), new EntryPoint(1));
        assertEquals(2, slotId);
        System.out.println("Allocated slot number:" + slotId);

        slotId = parkintLot.park(new Car("KA-01-BB-0001", "Black"), new EntryPoint(2));
        assertEquals(3, slotId);
        System.out.println("Allocated slot number:" + slotId);

        slotId = parkintLot.park(new Car("KA-01-HH-7777", "Red"), new EntryPoint(2));
        assertEquals(4, slotId);
        System.out.println("Allocated slot number:" + slotId);

        slotId = parkintLot.park(new Car("KA-01-HH-2701", "Blue"), new EntryPoint(3));
        assertEquals(5,slotId);
        System.out.println("Allocated slot number:" + slotId);

        slotId = parkintLot.park(new Car("KA-01-HH-3141", "Black"), new EntryPoint(3));
        assertEquals(6, slotId);
        System.out.println("Allocated slot number:" + slotId);
    }

    @Test
    public void status()
    {
        int slotId = parkintLot.leave(4);
        assertEquals(4, slotId);

        System.out.println("Slot number " + slotId + " is free");

        Map<Slot, Car> slotCarMap = parkintLot.getParkinglotStatus();

        System.out.println("Slot No" + "\t" + "Registration No." + "\t" + "Colour");
        for(Map.Entry<Slot, Car> e : slotCarMap.entrySet())
        {
            if(e.getValue() != null)
                System.out.println(e.getKey()+ "\t" + e.getValue().getRegistrationNumber() + "\t" + e.getValue().getColor());
        }

        try
        {
            parkintLot.park(new Car("KA-01-P-333", "White"), new EntryPoint(1));
            slotCarMap = parkintLot.getParkinglotStatus();
            assertEquals(6, slotCarMap.size());
        }
        catch (ParkingNotAvailableException e)
        {
            e.printStackTrace();

        }

        try
        {
            slotId = parkintLot.park(new Car("DL-12-AA-9999", "White"), new EntryPoint(1));
        }
        catch (ParkingNotAvailableException e)
        {
            System.out.println("Sorry, parking lot is full");
        }

        List<Car> whiteCars = parkintLot.getRegistrationNumbers("White");
        assertEquals(3, whiteCars.size());
        List<String> registrationNumbers = new ArrayList<String>();
        for(Car c : whiteCars)
        {
            registrationNumbers.add(c.getRegistrationNumber());
        }
        assertEquals(true, registrationNumbers.contains("KA-01-HH-1234"));
        assertEquals(true, registrationNumbers.contains("KA-01-HH-9999"));
        assertEquals(true, registrationNumbers.contains("KA-01-P-333"));

        for(String regNum : registrationNumbers)
        {
            System.out.print(regNum + ",");
        }

        //slot_numbers_for_cars_with_colour White
        List<Slot> whiteSlots =  parkintLot.getSlots("White");
        assertEquals(3, whiteSlots.size());
        for(Slot s : whiteSlots)
        {
            System.out.print(s.getId() + ",");
        }

        //slot_number_for_registration_number KA-01-HH-3141
        assertEquals(6, parkintLot.getSlotForCar("KA-01-HH-3141").getId());

        //slot_number_for_registration_number KA-01-HH-3141
        Slot slot = parkintLot.getSlotForCar("MH-04-AY-1111");
        assertEquals(null, slot);
        System.out.println("Not found");

    }


}
