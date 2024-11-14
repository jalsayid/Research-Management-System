package com.example.l;

import java.io.*;
import java.security.SecureRandom;
import java.util.Scanner;

public class Reservation {
    private String reservationID;
    private String date;
    private String machine;
    private String teamId;
    private String project;
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";
    String machineFileTxt=  "src/main/java/com/example/l/txt/MachineFile.txt";
    String MachineNamesFileTxt=  "src/main/java/com/example/l/txt/MachinesNamesFile.txt";
    String projectFileTxt=  "src/main/java/com/example/l/txt/Project.txt";
    String IntrestsFileTxt=  "src/main/java/com/example/l/txt/InterestFile.txt";
    String TeamsFileTxt=  "src/main/java/com/example/l/txt/TeamsFile.txt";
    String reservationFileTxt=  "src/main/java/com/example/l/txt/RecervationFile.txt";

    public Reservation(String reservationID, String project, String teamID, String date, String machine) {
        this.reservationID = reservationID;
        this.project = project;
        this.teamId = teamID;
        this.machine = machine;
        this.date = date;
    }

    public Reservation() {

    }
    public String getMachine() {
        return machine;
    }

    public String getTeamId() {return teamId;}
    public String getReservationID() { return reservationID; }

    public String getDate() {
        return date;
    }
    public String getProject() {
        return project;
    }

    public void addReservation(String project, String team, String date, String machine, String interest) throws IOException {
        // Generate a unique reservation ID (you can use UUID or any other method)
        this.reservationID = generateUniqueReservationID();

        // Create a string representation of the reservation
        String reservationData = reservationID + ", " + project + ", "+ team + ", " + date + ", " + machine;

        // Write the reservation data to the ReservationFile.txt
        writeToFile(reservationFileTxt, reservationData);
        updateAddMachineFile(machine, interest, date, reservationID);
        updateProjectFile(machine,project);
    }

    private String generateUniqueReservationID() {
        // Generate a random 5-character string for the reservation ID
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder(5);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }
        return randomString.toString();
    }

    private void writeToFile(String fileName, String data) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to handle it at a higher level if needed
        }
    }
    private void updateAddMachineFile(String machine, String interest, String date, String reservationId) throws IOException {
        Scanner sc = new Scanner(MachineNamesFileTxt);
        String machineId = "";
        while (sc.hasNext()){
            String[] line = sc.nextLine().split(",");
            if (line[0].equals(machine)){
                machineId += line[1];
            }

        }
        String data = machine + ", " + machineId + ", " + interest + ", " + date+ ", "+reservationId;
        writeToFile(machineFileTxt,data);
    }
    private void updateProjectFile(String machine, String project) throws IOException {
        try (Scanner read = new Scanner(new File(projectFileTxt))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(project)) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    int numMachine = Integer.parseInt(data[3]);
                    String machines = "";
                    for(int i=4; i>=(numMachine+4); i++){
                        machines += data[i]+", " ;
                    }
                    String str = data[0] + ", " + data[1] +", "+ data[2] + ", " + (Integer.parseInt(data[3])+1) + ", " +machine;
                    updatedFileContent.append(str).append(System.lineSeparator());
                }
            }
            read.close();

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File(projectFileTxt))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public boolean deleteReservation(String reservationID) throws IOException {
        Scanner scanner = new Scanner(new File(reservationFileTxt));
        String data = "";
        boolean found = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] info = line.split(", ");
            if (!info[0].trim().equals(reservationID)) {
                data+=line+"\n";
            }else {
                found=true;
            }
        }
        updateDeleteMachineFile(reservationID);
        scanner.close();
        if (found) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFileTxt));
            writer.write(data);
            writer.close();
        }
        return found;
    }
    private void updateDeleteMachineFile(String reservationId) throws IOException {
        Scanner sc = new Scanner(new File(machineFileTxt));

        StringBuilder updatedFileContent = new StringBuilder();

        String headerLine = sc.nextLine();

        updatedFileContent.append(headerLine).append(System.lineSeparator());

        while (sc.hasNext()){
            String line = sc.nextLine();
            String[] data = line.split(", ");

            if (!data[4].equals(reservationId)){
                updatedFileContent.append(line).append(System.lineSeparator());
            }
        }
        sc.close();

        Writer writer = new FileWriter(machineFileTxt);
        writer.write(updatedFileContent.toString());
        writer.close();
    }

    public boolean editReservation(String reservationID, String project, String team, String date, String machine, String interest)  throws IOException {
        boolean found = deleteReservation(reservationID);
        if (!found){
            return false;
        }else {
            addReservation(project, team,date, machine, interest);
            return true;
        }
    }

}


