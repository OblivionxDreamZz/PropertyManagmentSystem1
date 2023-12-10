package com.example.propertymanagmentsystem;

public class DataClass3 {


    private String name;

    private String address;

    private String listingPrice;

    private String propertyDescription;

    private String additionalInfo;

    private String key;

    private String client;
    private String contact;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }


    public DataClass3(String name, String address, String listingPrice, String propertyDescription, String additionalInfo, String client, String contact) {
        this.name = name;
        this.address = address;
        this.listingPrice = listingPrice;
        this.propertyDescription = propertyDescription;
        this.additionalInfo = additionalInfo;
        this.client = client;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getListingPrice() {
        return listingPrice;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getClient() {
        return client;
    }

    public String getContact() {
        return contact;
    }

    DataClass3(){

    }


}
