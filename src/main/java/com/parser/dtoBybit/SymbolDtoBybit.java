package com.parser.dtoBybit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SymbolDtoBybit {
    public String symbol;
    public String baseCoin;
    public String quoteCoin;
    public String innovation;
    public String status;
    public String marginTrading;
    public LotSizeFilter lotSizeFilter;
    public PriceFilter priceFilter;
    public String bid1Price;
    public String bid1Size;
    public String ask1Price;
    public String ask1Size;
    public String lastPrice;
    public String prevPrice24h;
    public String price24hPcnt;
    public String highPrice24h;
    public String lowPrice24h;
    public String turnover24h;
    public String volume24h;
    public String usdIndexPrice;
}
