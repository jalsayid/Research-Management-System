package com.example.l;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Java class representing a Member, inheriting from the User class
public class Member extends User {

    // Attributes
    private List<String> researchInterestList;
    private List<Member> members;
    private String name;
    private String email;
    private String id;
    private int numOfProjects;
    private int numOfTeams;
    private String researchInterests;

    // Constructor to create a Member with basic information
    public Member(String name, String email, String id) {
        super(name, email, id);
        this.researchInterestList = new ArrayList<>();
    }

    // Constructor to create a Member with detailed information
    public Member(String name, String id, String email, int numberOfProjects, int numOfTeams, String researchOfInterest) {
        super(name, id, email, researchOfInterest);
        this.numOfProjects = numberOfProjects;
        this.numOfTeams = numOfTeams;
    }

    // Method to get a list of teams associated with a member
    public static ArrayList<String> getTeams(String name) {
        ArrayList<String> teams = new ArrayList<>();
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/TeamsFile.txt"))) {
            read.nextLine();
            while (read.hasNext()) {
                String[] line = read.nextLine().split(", ");
                if (Arrays.asList(line).contains(name))
                    teams.add(line[0]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return teams;
    }

    // Getter method for the number of projects
    public int getNumOfProjects() {
        return this.numOfProjects;
    }

    // Getter method for the number of teams
    public int getNumOfTeams() {
        return this.numOfTeams;
    }

    // Getter method for the research interests list
    public List<String> getResearchInterestsList() {
        return researchInterestList;
    }

    // Setter method for the research interests list
    public void setResearchInterestsList(List<String> MemberResearchInterests) {
        this.researchInterestList = MemberResearchInterests;
    }

    // Method to get the ID of the most active member based on the number of projects
    public String getMostActiveMember() {
        Member mostActiveMember = members.get(0);
        for (Member member : members) {
            if (member.getNumOfProjects() > mostActiveMember.getNumOfProjects())
                mostActiveMember = member;
        }
        return mostActiveMember.getId();
    }

    // Method to get a list of members
    public List<Member> getListOfMembers() {
        List<Member> list = new ArrayList<>(members);
        return list;
    }

    // Method to add a new member to the system
    public static void addNewMember(Member member) {
        try (FileWriter fileWriter = new FileWriter("src/main/java/com/example/l/txt/Member.txt", true);
             PrintWriter write = new PrintWriter(fileWriter)) {
            write.print(member.toString());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

    // Method to remove a member from the system
    public static void remove(Member member) {
        try (Scanner read = new Scanner(new File("src/main/java/com/example/l/txt/Member.txt"))) {
            StringBuilder updatedFileContent = new StringBuilder();

            String headerLine = read.nextLine();
            updatedFileContent.append(headerLine).append(System.lineSeparator());

            while (read.hasNext()) {
                String line = read.nextLine();
                String[] data = line.split(", ");

                if (!data[0].equals(member.getName())) {
                    updatedFileContent.append(line).append(System.lineSeparator());
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

    // Override toString method to provide a string representation of the Member
    @Override
    public String toString() {
        String member = getName() + ", " + getId() + ", " + getEmail() + ", " + getNumOfProjects() + ", " + getNumOfTeams() + getResearchOfInterest();
        return member;
    }
}
