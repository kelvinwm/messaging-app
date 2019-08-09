package com.example.lenovo.timsntsaapp;

/**
 * Created by lenovo on 12/19/2017.
 */

public class DataModel {

        String name;
        String version_number;
        String ID;

        public  DataModel(){}
        public DataModel(String name,String getVersion_number ) {
            this.name=name;
            this.version_number=getVersion_number;

        }
        public String getName() {
            return name;
        }

        public String getVersion_number() {
            return version_number;
        }

    public void setName(String name) {
        this.name = name;
    }
    public void setVersion_number(String version_number) {
        this.version_number = version_number;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

