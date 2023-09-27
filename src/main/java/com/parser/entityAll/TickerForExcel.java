package com.parser.entityAll;

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
@Table(name = "excel")
public class TickerForExcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime time;

    private Double yobitPrice;
    private Double yobitVolume;
    private Double binancePrice;
    private Double binanceVolume;

    private Double bybitPrice;
    private Double byBitVolume;

    private Double poloniexPrice;
    private Double poloniexVolume;
}
