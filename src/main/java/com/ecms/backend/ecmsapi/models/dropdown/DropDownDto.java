package com.ecms.backend.ecmsapi.models.dropdown;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DropDownDto {
    private Long id;
    private String name;

    public DropDownDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
