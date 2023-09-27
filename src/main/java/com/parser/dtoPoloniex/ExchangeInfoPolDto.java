package com.parser.dtoPoloniex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeInfoPolDto {
    public String symbol;
    public String baseCurrencyName;
    public String quoteCurrencyName;
    public String displayName;
    public String state;
    public long visibleStartTime;
    public long tradableStartTime;
    public SymbolTradeLimit symbolTradeLimit;
    public CrossMargin crossMargin;
}
