package com.parser.dtoBinance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeInfoBinDto {
    private String timezone;
    private String serverTime;
    private List<RateLimitDto> rateLimits;
    private List<SymbolDto> symbols;
}
