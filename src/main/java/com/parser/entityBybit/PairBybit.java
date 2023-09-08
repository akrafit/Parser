package com.parser.entityBybit;

import com.parser.dtoBybit.SymbolDtoBybit;
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
public class PairBybit {
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

    public PairBybit(SymbolDtoBybit symbolDto) {
        this.code = generateCodeForBybit(symbolDto);
        this.symbol = symbolDto.symbol;
        this.baseAsset = symbolDto.baseCoin;
        this.minPrice = symbolDto.lotSizeFilter.minOrderAmt;
        this.maxPrice = symbolDto.lotSizeFilter.maxOrderAmt;
    }

    private String generateCodeForBybit(SymbolDtoBybit symbolDto) {
        return symbolDto.getBaseCoin() + "_" + symbolDto.getQuoteCoin();

    }
}
