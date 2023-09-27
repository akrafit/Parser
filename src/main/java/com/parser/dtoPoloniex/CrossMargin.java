package com.parser.dtoPoloniex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrossMargin {
    public boolean supportCrossMargin;
    public int maxLeverage;
}
