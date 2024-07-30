package com.ecms.backend.ecmsapi.models.complain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "complain_mst")
public class ComplainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String crimeType;

    @Column
    private String description;

    @Column
    private String location;

    @Column
    private String createdBy;

    @Column
    private LocalDate createdDate;

    @Column
    private String status;

    @Column
    private LocalDate updatedOn;


    @Column
    private String updateBy;
}
