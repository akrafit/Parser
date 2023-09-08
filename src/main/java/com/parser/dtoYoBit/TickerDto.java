package com.parser.dtoYoBit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TickerDto {
    private String high;
    private String low;
    private String avg;
    private String vol;
    private String vol_cur;
    private String last;
    private String buy;
    private String sell;
    private Integer updated;

    @Override
    public String toString() {
        return "TickerDto{" +
                "high=" + high +
                ", low=" + low +
                ", avg=" + avg +
                ", vol=" + vol +
                ", vol_cur=" + vol_cur +
                ", last=" + last +
                ", buy=" + buy +
                ", sell=" + sell +
                ", updated=" + updated +
                '}';
    }
}
