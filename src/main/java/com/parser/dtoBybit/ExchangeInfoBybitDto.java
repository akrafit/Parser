package com.parser.dtoBybit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeInfoBybitDto {
    public int retCode;
    public String retMsg;
    public ResultDtoBybit result;
    public RetExtInfo retExtInfo;
    public long time;
}
