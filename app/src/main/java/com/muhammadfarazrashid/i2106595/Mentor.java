package com.muhammadfarazrashid.i2106595;

public class Mentor {
    private String name;
    private String position;
    private String availability;
    private String salary;

    public Mentor(String name, String position, String availability, String salary) {
        this.name = name;
        this.position = position;
        this.availability = availability;
        this.salary = salary;
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

}
