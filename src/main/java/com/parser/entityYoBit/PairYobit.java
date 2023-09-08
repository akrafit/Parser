package com.parser.entityYoBit;

import com.parser.dtoYoBit.PairDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PairYobit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String pair;
    @Column(name = "decimal_places")
    private Integer decimalPlaces;
    @Column(name = "min_price")
    private String minPrice;
    @Column(name = "max_price")
    private String maxPrice;
    @Column(name = "min_amount")
    private String minAmount;
    private Integer hidden;
    private String fee;
    public PairYobit(String pair , PairDto pairDto) {
        this.pair = pair;
        this.decimalPlaces = pairDto.getDecimal_places();
        this.minPrice = pairDto.getMin_price();
        this.maxPrice = pairDto.getMax_price();
        this.minAmount = pairDto.getMin_amount();
        this.hidden = pairDto.getHidden();
        this.fee = pairDto.getFee();
        this.code = generateCodeFromYobit(pair);
    }

    private String generateCodeFromYobit(String pair) {
        return pair.toUpperCase();
    }

    @Override
    public String toString() {
        return "Pair{" +
                "decimalPlaces=" + decimalPlaces +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", minAmount='" + minAmount + '\'' +
                ", hidden=" + hidden +
                ", fee='" + fee + '\'' +
                '}';
    }
}
