package com.parser.dtoBinance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {
    private String filterType;
    private String minPrice;
    private String maxPrice;
    private String tickSize;
    private String minQty;
    private String maxQty;
    private String stepSize;
    private int limit;
    private int minTrailingAboveDelta;
    private int maxTrailingAboveDelta;
    private int minTrailingBelowDelta;
    private int maxTrailingBelowDelta;
    private String bidMultiplierUp;
    private String bidMultiplierDown;
    private String askMultiplierUp;
    private String askMultiplierDown;
    private int avgPriceMins;
    private String minNotional;
    private boolean applyMinToMarket;
    private String maxNotional;
    private boolean applyMaxToMarket;
    private int maxNumOrders;
    private int maxNumAlgoOrders;
}
