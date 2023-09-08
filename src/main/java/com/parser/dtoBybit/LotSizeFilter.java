package com.parser.dtoBybit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LotSizeFilter {
    public String basePrecision;
    public String quotePrecision;
    public String minOrderQty;
    public String maxOrderQty;
    public String minOrderAmt;
    public String maxOrderAmt;
}
