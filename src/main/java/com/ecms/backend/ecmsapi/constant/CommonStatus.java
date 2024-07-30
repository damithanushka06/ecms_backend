package com.ecms.backend.ecmsapi.constant;

public enum CommonStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    REJECTED("Rejected"),
    COMPLETED("Completed");

    private final String status;

    CommonStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
