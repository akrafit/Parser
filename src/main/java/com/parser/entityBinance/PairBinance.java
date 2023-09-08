package com.parser.entityBinance;

import com.parser.dtoBinance.SymbolDto;
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
@Table(name = "PairBinance")
public class PairBinance {
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
    public PairBinance(SymbolDto symbolDto) {
        this.symbol = symbolDto.getSymbol();
        this.status = symbolDto.getStatus();
        this.baseAsset = symbolDto.getBaseAsset();
        this.maxPrice = symbolDto.getFilters().get(0).getMaxPrice();
        this.minPrice = symbolDto.getFilters().get(0).getMinPrice();
        this.code = generateCodeForBinance(symbolDto);

    }

    private String generateCodeForBinance(SymbolDto symbol) {
        return symbol.getBaseAsset() + "_" + symbol.getSymbol().replaceAll(symbol.getBaseAsset(),"");
    }
}
