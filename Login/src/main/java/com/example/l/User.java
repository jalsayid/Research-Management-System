package com.example.l;

public class User {
    // Attributes
    private String name;
    private String email;
    private String id;

    private String researchOfInterest;
    // To build/create a user in the system we need its data
    public User(String name,String id,String email, String researchOfInterest) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.researchOfInterest = researchOfInterest;
    }
    public User(String id) {
        this.id = id;
    }

    // If user wants to change/update he's info
    public User(String name,String id,String email) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
    // Getters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }

    public String getResearchOfInterest() {
        return researchOfInterest;
    }
}
