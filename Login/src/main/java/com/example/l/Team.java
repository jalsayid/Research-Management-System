package com.example.l;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Team {
    private String teamID;
    private String leader;
    private String machine;
    private int numberOfMembers;

    private String members;
    private List<Team> teams = new ArrayList<>();;

    private String project;




    public Team(String teamID, String machine, String project ,String leader, String members) {
        this.teamID = teamID;
        this.machine = machine;
        this.project = project;
        this.leader = leader;
        this.members = members;

    }

    public Team(String testTeam) {
    }


    public String getTeamID() {
        return teamID;
    }

    public String getProject() {
        return project;
    }

    public String getLeader() {
        return leader;
    }
    public String getMachine(){return  machine;}

    public String getMembers() {
        return members;
    }

    public static List<String> getTeamMachines(String TeamID) {
        List<String> machines = new ArrayList<>();
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/RecervationFile.txt"))) {
            read.nextLine();
            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (data[2].equals(TeamID)) {
                    machines.add(data[4]);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
        return machines;
    }

    public static List<String> getTeamMembers(String TeamID) {
        List<String> membersNames = new ArrayList<>();
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            read.nextLine();
            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (data[0].equals(TeamID)) {
                    for (int i = 4; i < data.length; i++)
                        membersNames.add(data[i]);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
        return membersNames;
    }

    public String getTeamProjects() {
        return project;
    }
    public int getNumberOfMembers(){
        return numberOfMembers;
    }




    @Override
    public String toString() {
        String teamInfo = teamID + ", ";
        teamInfo +=  project +", ";
        teamInfo += getTeamMembers(teamID).size() + ", ";
        teamInfo +=  leader +", ";
        teamInfo +=  members +" ";
        teamInfo += "\n";
        // Add more project information as needed
        return teamInfo;
    }

    public List<Team> getListOfTeams() {
        return teams;
    }


    private static void updateProjectOnAdd(Team team) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[1].equals(team.getTeamID())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    String str = data[0] + ", " + team.getTeamID() + ", " + team.getLeader() + ", " + data[3];
                    updatedFileContent.append(str).append(System.lineSeparator());

                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File("src/main/java/com/example/l/txt/Project.txt"))) {
                writer.write(updatedFileContent.toString());
                System.out.println(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private static void updateMemberOnAdd(String memberNames) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Member.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!memberNames.contains(data[0])) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    String str = data[0] + ", " + data[1]  + ", "+  data[2] +", "+ data[3] + ", "+(Integer.parseInt(data[4])+1)+ ", " + data[5];
                    updatedFileContent.append(str).append(System.lineSeparator());
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File("src/main/java/com/example/l/txt/Member.txt"))) {
                writer.write(updatedFileContent.toString());
                System.out.println(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }


    private static void updateProjectOnRemove(Team team) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[1].equals(team.getTeamID())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                    System.out.println(updatedFileContent.toString());
                }
                else {
                    String str = data[0] + ", " + null + ", " + null + ", " + data[3];
                    updatedFileContent.append(str).append(System.lineSeparator());
                    System.out.println(str);

                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File("src/main/java/com/example/l/txt/Project.txt"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    private static void updateMemberOnRemove(String memberNames) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Member.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!memberNames.contains(data[0])) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                    System.out.println(updatedFileContent.toString());

                }
                else {
                    String str = data[0] + ", " + data[1]  + ", "+  data[2] +", "+ data[3]+ ", " +(Integer.parseInt(data[4])-1)+ ", "+ data[5];
                    updatedFileContent.append(str).append(System.lineSeparator());
                    System.out.println(str);
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File("src/main/java/com/example/l/txt/Member.txt"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }


    public static void addNewTeam(Team team) {
        try (FileWriter fileWriter = new FileWriter("src/main/java/com/example/l/txt/TeamsFile.txt", true);
             PrintWriter write = new PrintWriter(fileWriter)) {
            write.print(team.toString());
            updateProjectOnAdd(team);
            updateMemberOnAdd(team.getMembers());

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

    public static void remove(Team team) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            // Read and append the header line to the updated content
            if (read.hasNextLine()) {
                String headerLine = read.nextLine();
                updatedFileContent.append(headerLine).append(System.lineSeparator());
            }

            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(team.getTeamID())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter("src/main/java/com/example/l/txt/TeamsFile.txt")) {
                writer.write(updatedFileContent.toString());
                updateProjectOnRemove(team);
                updateMemberOnRemove(team.getMembers());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}