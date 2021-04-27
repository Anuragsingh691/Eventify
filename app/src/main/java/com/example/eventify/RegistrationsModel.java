package com.example.eventify;

public class RegistrationsModel {
    String branch,name,roll,year;

    public RegistrationsModel() {
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public RegistrationsModel(String branch, String name, String roll, String year) {
        this.branch = branch;
        this.name = name;
        this.roll = roll;
        this.year = year;
    }
}
