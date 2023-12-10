package com.example.propertymanagmentsystem.Model;


public class PropertyModel1 {

    private String Name, imageURL, Key, Description;
    private int Position;


    public PropertyModel1()
    {

    }

    public PropertyModel1(int Position)
    {
        this.Position = Position;
    }

    public PropertyModel1(String Name, String imageURL, String Key, String Description)
    {
        if(Name.trim().equals(""))
        {
            Name = "no name";
        }
        this.Name = Name;
        this.imageURL = imageURL;
        this.Description = Description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }


}
