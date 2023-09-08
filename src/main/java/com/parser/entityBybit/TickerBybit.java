package com.parser.entityBybit;

import com.parser.dtoBinance.SymbolDto;
import com.parser.dtoBybit.SymbolDtoBybit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TickerBybit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String priceChange;
    private String priceChangePercent;
    private String weightedAvgPrice;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private String lastPrice;
    private String volume;
    private String quoteVolume;
    private long openTime;
    private long closeTime;
    private int firstId;
    private int lastId;
    private int count;


    public TickerBybit(SymbolDtoBybit t) {
        this.symbol = t.getSymbol();
        this.priceChange = String.valueOf(Double.parseDouble(t.lastPrice) - Double.parseDouble(t.prevPrice24h));
        this.priceChangePercent = t.price24hPcnt;
        this.highPrice = t.highPrice24h;
        this.lowPrice = t.lowPrice24h;
        this.lastPrice = t.lastPrice;
        this.volume = t.volume24h;
    }
}
