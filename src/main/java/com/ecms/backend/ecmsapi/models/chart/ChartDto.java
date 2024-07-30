package com.ecms.backend.ecmsapi.models.chart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartDto {
    private int id;
    private long value;
    private String label;

    public ChartDto(int id, long value, String label) {
        this.id = id;
        this.value = value;
        this.label = label;
    }
}
