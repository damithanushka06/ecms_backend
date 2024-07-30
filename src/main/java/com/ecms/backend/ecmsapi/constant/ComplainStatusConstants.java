package com.ecms.backend.ecmsapi.constant;

public class ComplainStatusConstants {
    public static final String APPROVED = "A";
    public static final String REJECTED = "R";
    public static final String INVESTIGATION = "I";
    public static final String CLOSED = "C";

    public static String getStatus(String status) {
        switch (status) {
            case APPROVED:
                return "Approved";
            case REJECTED:
                return "Rejected";
            case INVESTIGATION:
                return "Investigation";
            case CLOSED:
                return "Closed";
            default:
                return "Unknown";
        }
    }
}
