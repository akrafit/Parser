package com.parser.entityYoBit;

import com.parser.dtoYoBit.TickerDto;
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
public class TickerYobit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String pair;
    private String high;
    private String low;
    private String avg;
    private String vol;
    @Column(name = "vol_cur")
    private String volCur;
    private String last;
    private String buy;
    private String sell;
    private Integer updated;

    public TickerYobit(String pairName, TickerDto tickerDto) {
        this.time = LocalDateTime.now();
        this.pair = pairName;
        this.high = tickerDto.getHigh();
        this.low = tickerDto.getLow();
        this.avg = tickerDto.getAvg();
        this.vol = tickerDto.getVol();
        this.volCur = tickerDto.getVol_cur();
        this.last = tickerDto.getLast();
        this.buy = tickerDto.getBuy();
        this.sell = tickerDto.getSell();
        this.updated = tickerDto.getUpdated();
    }
}
