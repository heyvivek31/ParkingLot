package com.vivek.parkinglot;

import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.EntryPoint;
import com.vivek.parkinglot.exception.ParkingNotAvailableException;
import com.vivek.parkinglot.service.impl.ParkingLotServiceImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkinglotTest {

    @Test
    public void testParkLeave()
    {
        ParkingLotServiceImpl pkLot = new ParkingLotServiceImpl();
        pkLot.createParkingLot(10, 3);

        for(int i = 1; i <= 10; i++)
        {
            try
            {
                pkLot.park(new Car("KA-10-ME-" + i, "White"), new EntryPoint(1));

            } catch (ParkingNotAvailableException e)
            {
                e.printStackTrace();
            }
        }

        //Now try to park 11th car and it should say parking full
        try
        {
            pkLot.park(new Car("KA-10-ME-" + 11, "White"), new EntryPoint(1));

        } catch (Exception e)
        {
            assertTrue(e instanceof ParkingNotAvailableException);

        }
        //Leave at 1,3,5 slots
        pkLot.leave(5);
        pkLot.leave(1);
        pkLot.leave(3);
    }

    @Test
    public void testGetCarsByColor()
    {
        ParkingLotServiceImpl pkLot = new ParkingLotServiceImpl();
        pkLot.createParkingLot(15, 3);

        for(int i = 1; i <= 10; i++)
        {
            try
            {
                pkLot.park(new Car("KA-10-ME-" + i, "White"), new EntryPoint(1));
            }
            catch (ParkingNotAvailableException e)
            {
                e.printStackTrace();

            }
        }
        for(int i = 11; i <= 15; i++)
        {
            try
            {
                pkLot.park(new Car("KA-10-ME-" + i, "Black"), new EntryPoint(2));
            }
            catch (ParkingNotAvailableException e)
            {
                e.printStackTrace();

            }
        }
        List<Car> whiteCars = pkLot.getRegistrationNumbers("White");
        assertEquals(10, whiteCars.size());

        List<Car> blackCars = pkLot.getRegistrationNumbers("Black");
        assertEquals(5, blackCars.size());
    }

    @Test
    public void testGetSlotForCar()
    {
        ParkingLotServiceImpl pkLot = new ParkingLotServiceImpl();
        pkLot.createParkingLot(10, 3);

        for(int i = 1; i <= 10; i++)
        {
            try
            {
                pkLot.park(new Car("KA-10-ME-" + i, "White"), new EntryPoint(1));
            }
            catch (ParkingNotAvailableException e)
            {
                e.printStackTrace();

            }
        }

        assertEquals(1, pkLot.getSlotForCar("KA-10-ME-1").getId());
        assertEquals(5, pkLot.getSlotForCar("KA-10-ME-5").getId());
        assertEquals(10, pkLot.getSlotForCar("KA-10-ME-10").getId());
    }

}
