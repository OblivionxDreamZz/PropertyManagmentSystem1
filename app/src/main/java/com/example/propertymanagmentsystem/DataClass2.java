package com.example.propertymanagmentsystem;

public class DataClass2
{
    private String name;
    private String reference;
    private String address;
    private String reporting;

    private String progress;


    private String key;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public DataClass2(String name, String reference, String address, String reporting) {
        this.name = name;
        this.reference = reference;
        this.address = address;
        this.reporting = reporting;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }

    public String getAddress() {
        return address;
    }

    public String getReporting() {
        return reporting;
    }

    DataClass2()
    {

    }


}
