package com.parser.dtoBinance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TickerBinanceDto {
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

    @Override
    public String toString() {
        return "TickerBinanceDto{" +
                "symbol='" + symbol + '\'' +
                ", priceChange='" + priceChange + '\'' +
                ", priceChangePercent='" + priceChangePercent + '\'' +
                ", weightedAvgPrice='" + weightedAvgPrice + '\'' +
                ", openPrice='" + openPrice + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", volume='" + volume + '\'' +
                ", quoteVolume='" + quoteVolume + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", firstId=" + firstId +
                ", lastId=" + lastId +
                ", count=" + count +
                '}';
    }
}
