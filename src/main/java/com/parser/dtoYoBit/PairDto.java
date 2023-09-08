package com.parser.dtoYoBit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PairDto {
    //private String pair;
    private Integer decimal_places;
    private String min_price;
    private String max_price;
    private String min_amount;
    private Integer hidden;
    private String fee;

    @Override
    public String toString() {
        return "Pair{" +
                "decimal_places=" + decimal_places +
                ", min_price='" + min_price + '\'' +
                ", max_price='" + max_price + '\'' +
                ", min_amount='" + min_amount + '\'' +
                ", hidden=" + hidden +
                ", fee='" + fee + '\'' +
                '}';
    }
}
