package com.hyt.bmc208_assignment;

//add new healthcare centre java class (no xml)
public class Healthcare_centre {

    static String COLLECTION_NAME = "Centres";

    private String centre_ID;
    private String centre_name;
    private String centre_address;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public static void setCollectionName(String collectionName) {
        COLLECTION_NAME = collectionName;
    }

    public String getCentre_ID() {
        return centre_ID;
    }

    public void setCentre_ID(String centre_ID) {
        this.centre_ID = centre_ID;
    }

    public String getCentre_name() {
        return centre_name;
    }

    public void setCentre_name(String centre_name) {
        this.centre_name = centre_name;
    }

    public String getCentre_address() {
        return centre_address;
    }

    public void setCentre_address(String centre_address) {
        this.centre_address = centre_address;
    }
}
