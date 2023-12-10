package com.example.propertymanagmentsystem;

public class DataClass5 {

    String name;
    String email;
    String company;
    String password;

    //Entry point constructor
    public DataClass5()
    {

    }

    //Constructor
    public DataClass5(String name, String email, String company, String password)
    {
        this.name = name;
        this.email = email;
        this.company = company;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
