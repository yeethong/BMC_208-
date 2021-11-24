package com.hyt.bmc208_assignment;

public class AstraBatch {

    static String COLLECTION_NAME = "AstraZeneca_Batch";

    private String astraZeneca_batch_ID;
    private String BatchNo;
    private String expiryDate;
    private String quantityAvailable;
    private String centreName;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public static void setCollectionName(String collectionName) {
        COLLECTION_NAME = collectionName;
    }

    public String getAstraZeneca_batch_ID() {
        return astraZeneca_batch_ID;
    }

    public void setAstraZeneca_batch_ID(String astraZeneca_batch_ID) {
        this.astraZeneca_batch_ID = astraZeneca_batch_ID;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }
}
