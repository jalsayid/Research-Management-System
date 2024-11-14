package com.example.l;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project {
    String MachineFileTxt=  "src/main/java/com/example/l/txt/MachineFile.txt";
    String MachineNamesFileTxt=  "src/main/java/com/example/l/txt/MachinesNamesFile.txt";
    String ProjectFileTxt=  "src/main/java/com/example/l/txt/Project.txt";
    String IntrestsFileTxt=  "src/main/java/com/example/l/txt/InterestFile.txt";
    String TeamsFileTxt=  "src/main/java/com/example/l/txt/TeamsFile.txt";
    String ReservationTxt=  "src/main/java/com/example/l/txt/RecervationFile.txt";
    String MemberFileTxt=  "src/main/java/com/example/l/txt/Member.txt";

    private String projectName;
    private String teamID;
    private String leader;
    private static List<Project> projects = new ArrayList<>();
    private int machineUsage;

    public Project(String projectName, String teamID, String leader,int machineUsage) {
        this.projectName = projectName;
        this.teamID = teamID;
        this.leader = leader;
        this.machineUsage = machineUsage;
    }
    // setters
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setLeader(String leader) {this.leader = leader;}
    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public void setMachineUsage(int machineUsage) {
        this.machineUsage = machineUsage;
    }
    public String getProjectName() {
        return projectName;
    }

    public String getLeader() {
        return leader;
    }

    public String getTeamID() {
        return teamID;
    }

    public int getMachineUsage() {
        return machineUsage;
    }

    public static void addNewProject(Project project) {
        try (FileWriter fileWriter = new FileWriter("src/main/java/com/example/l/txt/Project.txt", true);
             PrintWriter write = new PrintWriter(fileWriter)) {
            write.print(project.toString());
            updateTeamFile1(project);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }
    private static void updateTeamFile1(Project project) {
        try (Scanner read = new Scanner(new File( "src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(project.getTeamID())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    String str = data[0] + ", " + project.getProjectName() + ", ";
                    for (int i = 2; i < data.length - 1; i++)
                        str += data[i] + ", ";
                    str += data[data.length - 1];
                    updatedFileContent.append(str).append(System.lineSeparator());
                    for(int i = 3; i <  data.length; i++)
                        updateMemberFile1(data[i]);
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File( "src/main/java/com/example/l/txt/TeamsFile.txt"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    private static void updateMemberFile1(String memberName) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Member.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(memberName)) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    String str = data[0] + ", " + data[1] +", "+ data[2] + ", " + (Integer.parseInt(data[3])+1) + ", " + data[4];
                    updatedFileContent.append(str).append(System.lineSeparator());
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File( "src/main/java/com/example/l/txt/Member.txt"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    public static void remove(Project project) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] projectData = line.split(", ");

                if (!projectData[0].equals(project.getProjectName())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File("src/main/java/com/example/l/txt/Project.txt"))) {
                writer.write(updatedFileContent.toString());
                updateTeamFile2(project);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    private static void updateTeamFile2(Project project) {
        try (Scanner read = new Scanner(new File( "src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(project.getTeamID())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    String str = data[0] + ", " + null + ", ";
                    for (int i = 2; i < data.length - 1; i++)
                        str += data[i] + ", ";
                    str += data[data.length - 1];
                    updatedFileContent.append(str).append(System.lineSeparator());
                    for(int i = 3; i <  data.length; i++)
                        updateMemberFile2(data[i]);
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File( "src/main/java/com/example/l/txt/TeamsFile.txt"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    private static void updateMemberFile2(String memberName) {
        try (Scanner read = new Scanner(new File( "src/main/java/com/example/l/txt/Member.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(memberName)) {
                    updatedFileContent.append(line).append(System.lineSeparator());
                }
                else {
                    String str = data[0] + ", " + data[1] +", "+ data[2] + ", " + (Integer.parseInt(data[3])-1) + ", " + data[4];
                    updatedFileContent.append(str).append(System.lineSeparator());
                }
            }

            // Write the updated content back to the file
            try (PrintWriter writer = new PrintWriter(new File( "src/main/java/com/example/l/txt/Member.txt"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    public static String getProjectUsesMostMachines() {
        int maxMachineUsage = Integer.MIN_VALUE;
        Project mostMachineProject = null;
        for (Project project : getListOfProjects()) {
            if (project.machineUsage > maxMachineUsage) {
                maxMachineUsage = project.machineUsage;
                mostMachineProject = project;
            }
        }
        if (mostMachineProject != null) {
            return mostMachineProject.projectName;
        } else {
            return null;
        }
    }
    @Override
    public String toString() {
        String projectInfo = projectName + ", ";
        projectInfo += teamID + ", ";
        projectInfo += leader + ", " + machineUsage +"\n";
        // Add more project information as needed
        return projectInfo;
    }
    public static List<Project> getListOfProjects() {
        try(Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Project.txt"))){
            read.nextLine();
            while (read.hasNext()) {
                String[] line = read.nextLine().split(", ");
                projects.add(new Project(line[0], line[1], line[2], Integer.parseInt(line[3])));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }

        return projects;
    }

}
