package com.parser.dtoBinance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RateLimitDto {
    private String rateLimitType;
    private String interval;
    private Integer intervalNum;
    private Integer limit;
}
