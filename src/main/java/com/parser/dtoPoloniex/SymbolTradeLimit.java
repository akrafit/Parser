package com.parser.dtoPoloniex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SymbolTradeLimit {
    public String symbol;
    public int priceScale;
    public int quantityScale;
    public int amountScale;
    public String minQuantity;
    public String minAmount;
    public String highestBid;
    public String lowestAsk;
}
