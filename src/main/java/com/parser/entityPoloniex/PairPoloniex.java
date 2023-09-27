package com.parser.entityPoloniex;

import com.parser.dtoPoloniex.ExchangeInfoPolDto;
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
public class PairPoloniex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String symbol;
    private String status;
    @Column(name = "base_asset")
    private String baseAsset;
    @Column(name = "min_price")
    private String minPrice;
    @Column(name = "max_price")
    private String maxPrice;

    public PairPoloniex(ExchangeInfoPolDto exchangeInfoPolDto) {
        this.code = exchangeInfoPolDto.getSymbol();
        this.symbol = exchangeInfoPolDto.getSymbol();
        this.baseAsset = exchangeInfoPolDto.getBaseCurrencyName();
        this.minPrice = exchangeInfoPolDto.getSymbolTradeLimit().getMinQuantity();
        this.maxPrice = "1000";//нету данных

    }
}
