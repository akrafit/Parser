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
public class YoBitInfoDto {

    private Integer server_time;
    private Map<String, PairDto> pairs;
}
