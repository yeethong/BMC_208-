package com.hyt.bmc208_assignment;

public class PfizerBatch {

    static String COLLECTION_NAME = "Pfizer_Batch";

    private String pfizer_batch_ID;
    private String BatchNo;
    private String expiryDate;
    private String quantityAvailable;
    private String centreName;

    public String getPfizer_batch_ID() {
        return pfizer_batch_ID;
    }

    public void setPfizer_batch_ID(String pfizer_batch_ID) {
        this.pfizer_batch_ID = pfizer_batch_ID;
    }

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public static void setCollectionName(String collectionName) {
        COLLECTION_NAME = collectionName;
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
