package com.parser.dtoYoBit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YoBitTickerDto {
    private Map<String,TickerDto> ticker;
}
