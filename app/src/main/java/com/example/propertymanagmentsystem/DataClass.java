package com.example.propertymanagmentsystem;

public class DataClass {

    private String name;

    private String address;

    private String listingPrice;

    private String propertyDescription;

    private String additionalInfo;

    private String dataImage;

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    private String key;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
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

    public String getDataImage() {
        return dataImage;
    }

    public DataClass(String name, String address, String listingPrice, String propertyDescription, String additionalInfo, String dataImage)
    {
        this.name = name;
        this.address = address;
        this.listingPrice = listingPrice;
        this.propertyDescription = propertyDescription;
        this.additionalInfo = additionalInfo;
        this.dataImage = dataImage;
    }

    public DataClass(){


    }

}
