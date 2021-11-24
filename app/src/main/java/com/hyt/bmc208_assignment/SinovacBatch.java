package com.hyt.bmc208_assignment;

public class SinovacBatch {

    static String COLLECTION_NAME = "Sinovac_Batch";

    private String sinovac_batch_ID;
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

    public String getSinovac_batch_ID() {
        return sinovac_batch_ID;
    }

    public void setSinovac_batch_ID(String sinovac_batch_ID) {
        this.sinovac_batch_ID = sinovac_batch_ID;
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
