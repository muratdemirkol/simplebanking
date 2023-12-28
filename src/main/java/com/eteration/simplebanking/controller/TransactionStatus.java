package com.eteration.simplebanking.controller;


// This class is a place holder you can change the complete implementation

import lombok.Data;

@Data
public class TransactionStatus {
    private String status;
    private String approvalCode;

    public TransactionStatus(String status, String approvalCode) {
        this.status = status;
        this.approvalCode = approvalCode;
    }
}
