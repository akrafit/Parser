package com.parser.dtoBinance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SymbolDto {
    private String symbol;
    private String status;
    private String baseAsset;
    private int baseAssetPrecision;
    private String quoteAsset;
    private int quotePrecision;
    private int quoteAssetPrecision;
    private int baseCommissionPrecision;
    private int quoteCommissionPrecision;
    private ArrayList<String> orderTypes;
    private boolean icebergAllowed;
    private boolean ocoAllowed;
    private boolean quoteOrderQtyMarketAllowed;
    private boolean allowTrailingStop;
    private boolean cancelReplaceAllowed;
    private boolean isSpotTradingAllowed;
    private boolean isMarginTradingAllowed;
    private ArrayList<FilterDto> filters;
    private ArrayList<String> permissions;
    private String defaultSelfTradePreventionMode;
    private ArrayList<String> allowedSelfTradePreventionModes;
}
