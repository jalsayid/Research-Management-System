package com.example.l;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Java class representing a Machine
public class Machine {
    // Instance variables
    private String name;
    private String machineID;
    private int machine_Id;
    private String usage;
    private boolean availability;
    private static List<Machine> machines;  // List to store instances of machines
    static String machineFile = "src/main/java/com/example/l/txt/MachinesNamesFile.txt";  // File path for machine data

    // Constructors
    public Machine(String name, String machineIDstr, String usage, boolean availability) {
        this.name = name;
        this.machineID = machineIDstr;
        this.usage = usage;
        this.availability = availability;
        this.machines = new ArrayList<>();  // Initialize the list in the constructor
    }

    public Machine() {
    }

    public Machine(String name, String machineID, String usage) {
        this.name = name;
        this.machineID = machineID;
        this.usage = usage;
    }

    // Method to get the ID of the most utilized machine
    public int getMostUtilizedMachine() {
        int mostUtilizedMachineID = -1;
        int maxUsage = Integer.MIN_VALUE;

        for (Machine machine : machines) {
            int usage = numberOfUsage(machine.getMachine_Id());
            if (usage > maxUsage) {
                maxUsage = usage;
                mostUtilizedMachineID = machine.getMachine_Id();
            }
        }

        return mostUtilizedMachineID;
    }

    // Method to check if a machine is available
    public boolean isAvailable(int machineID) {
        for (Machine machine : machines) {
            if (machine.getMachine_Id() == machineID) {
                return machine.isAvailability();
            }
        }
        return false;
    }

    // Method to get the number of times a machine is used
    public int numberOfUsage(int machineID) {
        int count = 0;
        for (Machine machine : machines) {
            if (machine.getMachine_Id() == machineID) {
                count++;
            }
        }
        return count;
    }

    // Static method to add a machine to the list
    public static void addMachine(Machine machine) {
        machines.add(machine);
    }

    // Method to set the work schedule for a machine
    public void setMachineWorkSchedule(String timeSlots) {
        // Implementation needed based on requirements
    }

    // Method to get the usage of a machine by ID
    public String getMachineUsage(int machineID) {
        for (Machine machine : machines) {
            if (machine.getMachine_Id() == machineID) {
                return machine.getUsage();
            }
        }
        return null;
    }

    // Method to print information about machines used by a team
    public void getUsedMachine(int teamID) {
        for (Machine machine : machines) {
            if (machine.numberOfUsage(machine.getMachine_Id()) > 0) {
                System.out.println("Machine " + machine.getName() + " is used by team " + teamID);
            }
        }
    }

    // Static method to delete a machine from the file
    public static void deleteMachine(String machineID) {
        try (Scanner read = new Scanner(new File(machineFile))) {
            StringBuilder updatedFileContent = new StringBuilder();

            // Read the header line and append it to the updated content
            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            // Iterate through the file, skip the line with the specified machine ID, and append the rest to the updated content
            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[1].equals(machineID)) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(machineFile)) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getMachine_Id() {
        return machine_Id;
    }

    public String getMachineId() {
        return machineID;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isAvailability() {
        return availability;
    }

    public List<Machine> getMachines() {
        return machines;
    }
}
