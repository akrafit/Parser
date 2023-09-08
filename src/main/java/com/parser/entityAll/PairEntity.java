package com.parser.entityAll;

import com.parser.entityBinance.PairBinance;
import com.parser.entityBybit.PairBybit;
import com.parser.entityYoBit.PairYobit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pair")
public class PairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private LocalDateTime time;

    private String yobitNameId;
    private String yobitPrice;
    private String yobitVolume;
    private String binanceNameId;
    private String binancePrice;
    private String binanceVolume;

    private String bybitNameId;
    private String bybitPrice;
    private String byBitVolume;

    public PairEntity(PairBinance pairBinance, PairYobit pairYobit, PairBybit pairBybit) {
        this.code = pairBinance.getCode();
        this.time = LocalDateTime.now();
        this.binanceNameId = pairBinance.getSymbol();
        this.yobitNameId = pairYobit.getPair();
        this.bybitNameId = pairBybit.getSymbol();


    }
}
