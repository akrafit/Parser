package com.parser.entityBinance;

import com.parser.dtoBinance.TickerBinanceDto;
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
public class TickerBinance {
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
    private String firstId;
    private String lastId;
    private int count;

    public TickerBinance(TickerBinanceDto t) {
        this.symbol = t.getSymbol();
        this.priceChange = t.getPriceChange();
        this.priceChangePercent = t.getPriceChangePercent();
        this.weightedAvgPrice = t.getWeightedAvgPrice();
        this.openPrice = t.getOpenPrice();
        this.highPrice = t.getHighPrice();
        this.lowPrice = t.getLowPrice();
        this.lastPrice = t.getLastPrice();
        this.volume = t.getVolume();
        this.quoteVolume = t.getQuoteVolume();
        this.openTime = t.getOpenTime();
        this.closeTime = t.getCloseTime();
        this.firstId = t.getFirstId();
        this.lastId = t.getLastId();
        this.count = t.getCount();
    }
}
