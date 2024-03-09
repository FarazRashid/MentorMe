package com.muhammadfarazrashid.i2106595;

public class Mentor {

    private String id;
    private String name;
    private String position;
    private String availability;
    private String salary;

    private String description;

    public Mentor(String id, String name, String position, String availability, String salary,String description) {
        this.name = name;
        this.position = position;
        this.availability = availability;
        this.salary = salary;
        this.description = description;

    }

    public Mentor(String name, String position, String availability, String salary) {
        this.name = name;
        this.position = position;
        this.availability = availability;
        this.salary = salary;
        this.description = "No description available";

    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getAvailability() {
        return availability;
    }

    public String getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
