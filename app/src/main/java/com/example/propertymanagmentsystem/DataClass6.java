package com.example.propertymanagmentsystem;

public class DataClass6 {

        private String name;
        private String reference;
        private String address;
        private String reporting;

        private String progress;

        public String getProgress() {
        return progress;
    }

        public void setProgress(String progress) {
        this.progress = progress;
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

    public DataClass6(String name, String reference, String address, String reporting, String progress)
    {
        this.name = name;
        this.reference = reference;
        this.address = address;
        this.reporting = reporting;
        this.progress = progress;
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

        DataClass6()
        {

        }


    }
