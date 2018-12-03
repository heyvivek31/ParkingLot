package com.vivek.parkinglot;

import com.vivek.parkinglot.model.Car;
import com.vivek.parkinglot.model.EntryPoint;
import com.vivek.parkinglot.model.Slot;
import com.vivek.parkinglot.exception.ParkingNotAvailableException;
import com.vivek.parkinglot.service.ParkingLot;
import com.vivek.parkinglot.service.impl.ParkingLotServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StartParkingLot {

    static ParkingLot parkingLot;
    public static void main(String[] args)
    {
        parkingLot = new ParkingLotServiceImpl();

        System.out.println("*********************Welcome to Parking Lot******************");
        printAvailableCommands();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String commandln = scanner.nextLine();
            processCommand(commandln);
        }
    }
    private static void printAvailableCommands()
    {
        System.out.println("Available commands:" );
        System.out.println("create_parking_lot <slots> <entrypoints>");
        System.out.println("park <RegistrationNumber> <Color> <entrypoint_id>");
        System.out.println("leave <slot>");
        System.out.println("status");
        System.out.println("registration_numbers_for_cars_with_colour <color>");
        System.out.println("slot_numbers_for_cars_with_colour <color>");
        System.out.println("slot_number_for_registration_number <registrationNumber>");
    }
    private static void create_parking_lot(int nrOfSlots, int entryPoints)
    {
        parkingLot.createParkingLot(nrOfSlots, entryPoints);
        System.out.println("Created a parking lot with " + nrOfSlots + " slots and " + entryPoints + " entry points");
    }
    private static void park(String registrationNumber, String color, int entryPoint)
    {
        try
        {
            int slotId = parkingLot.park(new Car(registrationNumber, color), new EntryPoint(entryPoint));
            System.out.println("Allocated slot number: " + slotId);
        }
        catch (ParkingNotAvailableException e)
        {
            System.out.println("Sorry, parking lot is full");
        }
    }
    private static void leave(int slotId)
    {
        parkingLot.leave(slotId);
        System.out.println("Slot number " + slotId + " is free");
    }
    private static void status()
    {
        Map<Slot, Car> slotCarMap = parkingLot.getParkinglotStatus();

        System.out.println("Slot No" + "\t" + "Registration No." + "\t" + "Colour");
        for(Map.Entry<Slot, Car> e : slotCarMap.entrySet())
        {
            if(e.getValue() != null)
            {
                System.out.println(e.getKey().getId() + "\t" + e.getValue().getRegistrationNumber() + "\t" + e.getValue().getColor());
            }
        }
    }
    private static void registration_numbers_for_cars_with_colour(String color)
    {
        List<Car> whiteCars = parkingLot.getRegistrationNumbers(color);
        List<String> registrationNumbers = new ArrayList<String>();
        for(Car c : whiteCars)
        {
            registrationNumbers.add(c.getRegistrationNumber());
        }
        StringBuffer sb = new StringBuffer();
        for(int c = 0; c < registrationNumbers.size(); c++)
        {
            sb.append(registrationNumbers.get(c));
            if(c != registrationNumbers.size()-1)
            {
                sb.append(", ");
            }
        }
        System.out.println(sb.toString());
    }
    private static void slot_numbers_for_cars_with_colour(String color)
    {
        List<Slot> whiteSlots =  parkingLot.getSlots(color);
        StringBuffer sb = new StringBuffer();
        for(int c = 0; c < whiteSlots.size(); c++)
        {
            sb.append(whiteSlots.get(c).getId());
            if(c != whiteSlots.size()-1)
            {
                sb.append(", ");
            }
        }
        System.out.println(sb.toString());
    }
    private static void slot_number_for_registration_number(String registrationNumber)
    {
        Slot s = parkingLot.getSlotForCar(registrationNumber);
        if(s == null)
        {
            System.out.println("Not found");
        }
        else
        {
            System.out.println(s.getId());
        }
    }
    private static void processCommand(String commandln)
    {
        String[] commandInput = commandln.split(" ");
        String command = commandInput[0];

        if("create_parking_lot".equals(command))
        {
            if(commandInput.length != 3)
            {
                System.out.println("Invalid input");
            }
            else
            {
                int nrOfSlots = Integer.parseInt(commandInput[1]);
                int noOfEntryPoints = Integer.parseInt(commandInput[2]);
                create_parking_lot(nrOfSlots, noOfEntryPoints);
            }
        }
        else if("park".equals(command))
        {
            if(commandInput.length != 4)
            {
                System.out.println("Invalid input");
            }
            else
            {
                String registrationNumber = commandInput[1];
                String color = commandInput[2];
                int entryPoint = Integer.parseInt(commandInput[3]);
                park(registrationNumber, color, entryPoint);
            }
        }
        else if("leave".equals(command))
        {
            if(commandInput.length != 2)
            {
                System.out.println("Invalid input");
            }
            else
            {
                int slotId = Integer.parseInt(commandInput[1]);
                leave(slotId);
            }
        }
        else if("status".equals(command))
        {
            if(commandInput.length != 1)
            {
                System.out.println("Invalid input");
            }
            else
            {
                status();
            }
        }
        else if("registration_numbers_for_cars_with_colour".equals(command))
        {
            if(commandInput.length != 2)
            {
                System.out.println("Invalid input");
            }
            else
            {
                String color = commandInput[1];
                registration_numbers_for_cars_with_colour(color);
            }
        }
        else if("slot_numbers_for_cars_with_colour".equals(command))
        {
            if(commandInput.length != 2)
            {
                System.out.println("Invalid input");
            }
            else
            {
                String color = commandInput[1];
                slot_numbers_for_cars_with_colour(color);
            }
        }
        else if("slot_number_for_registration_number".equals(command))
        {
            if(commandInput.length != 2)
            {
                System.out.println("Invalid input");
            }
            else
            {
                String regNo = commandInput[1];
                slot_number_for_registration_number(regNo);
            }
        }
        else
        {
            System.out.println("Invalid command");
        }

    }

}
