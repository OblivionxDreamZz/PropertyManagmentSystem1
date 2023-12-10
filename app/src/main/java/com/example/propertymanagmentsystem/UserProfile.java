package com.example.propertymanagmentsystem;

public class UserProfile {

    private String Agent_Name;
    private String Company_Name;
    private String Email_Address;
    private String Password;

    UserProfile(){

    }

    public UserProfile(String agent_Name, String company_Name, String email_Address, String password) {
        this.Agent_Name = agent_Name;
        this.Company_Name = company_Name;
        this.Email_Address = email_Address;
        this.Password = password;
    }

    public String getAgent_Name() {
        return Agent_Name;
    }

    public void setAgent_Name(String agent_Name) {
        this.Agent_Name = agent_Name;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        this.Company_Name = company_Name;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        this.Email_Address = email_Address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
