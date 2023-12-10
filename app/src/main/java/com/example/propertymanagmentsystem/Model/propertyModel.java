package com.example.propertymanagmentsystem.Model;

public class propertyModel {

    private String name,listing, address, description, addInfo, propertyDocID, propertyImg, currentUserID;


    public propertyModel(String s, String name, String listing, String address, String description, String addInfo, String propertyDocID, String propertyImg, String currentUserID) {
        this.name = name;
        this.listing = listing;
        this.address = address;
        this.description = description;
        this.addInfo = addInfo;
        this.propertyDocID = propertyDocID;
        this.propertyImg = propertyImg;
        this.currentUserID = currentUserID;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListing() {
        return listing;
    }

    public void setListing(String listing) {
        this.listing = listing;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getPropertyDocID() {
        return propertyDocID;
    }

    public void setPropertyDocID(String propertyDocID) {
        this.propertyDocID = propertyDocID;
    }

    public String getPropertyImg() {
        return propertyImg;
    }

    public void setPropertyImg(String propertyImg) {
        this.propertyImg = propertyImg;
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }
}
