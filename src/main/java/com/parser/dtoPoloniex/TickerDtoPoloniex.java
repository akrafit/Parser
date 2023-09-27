package com.parser.dtoPoloniex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TickerDtoPoloniex {
    public String symbol;
    public String price;
    public long time;
    public String dailyChange;
    public long ts;
}
