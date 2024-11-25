package com.todoapp.model;

public class User {
    private String id;
    private String name;
    private String companyId;
    private UserType type;

    // No-argument constructor
    public User() {}

    // Constructor with parameters
    public User(String id, String name, String companyId, UserType type) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
