package com.hyt.bmc208_assignment;

//patient java class (no xml)
public class Patient {

    static String COLLECTION_NAME = "Patients";

    private String patient_ID;
    private String patient_username;
    private String patient_password;
    private String patient_email;
    private String patient_fullName;
    private String patient_icPassport;

    public String getPatient_ID() {
        return patient_ID;
    }

    public void setPatient_ID(String patient_ID) {
        this.patient_ID = patient_ID;
    }

    public String getPatient_username() {
        return patient_username;
    }

    public void setPatient_username(String patient_username) {
        this.patient_username = patient_username;
    }

    public String getPatient_password() {
        return patient_password;
    }

    public void setPatient_password(String patient_password) {
        this.patient_password = patient_password;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public String getPatient_fullName() {
        return patient_fullName;
    }

    public void setPatient_fullName(String patient_fullName) {
        this.patient_fullName = patient_fullName;
    }

    public String getPatient_icPassport() {
        return patient_icPassport;
    }

    public void setPatient_icPassport(String patient_icPassport) {
        this.patient_icPassport = patient_icPassport;
    }
}
