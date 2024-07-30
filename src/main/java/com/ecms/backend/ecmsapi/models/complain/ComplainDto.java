package com.ecms.backend.ecmsapi.models.complain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ComplainDto {

    private Long id;

    private String crimeType;

    private String description;

    private String location;

    private String createdBy;

    private LocalDate createdDate;

    private String status;

    private LocalDate updatedOn;

    public ComplainDto(Long id, String crimeType, String description, String location,
                       String createdBy, LocalDate createdDate, String status, LocalDate updatedOn) {
        this.id = id;
        this.crimeType = crimeType;
        this.description = description;
        this.location = location;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.status = status;
        this.updatedOn = updatedOn;
    }
}
