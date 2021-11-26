package com.hyt.bmc208_assignment;

import java.io.Serializable;

public class SelectedBatchNo implements Serializable {

    String batchNo;
    String batchStatus;
    String batchQuantityAvailable;

    public SelectedBatchNo(String batchNo, String batchStatus, String batchQuantityAvailable){
        this.batchNo = batchNo;
        this.batchStatus = batchStatus;
        this.batchQuantityAvailable = batchQuantityAvailable;
    }

}
