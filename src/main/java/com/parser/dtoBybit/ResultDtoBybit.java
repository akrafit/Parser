package com.parser.dtoBybit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDtoBybit {
    public String category;
    public ArrayList<SymbolDtoBybit> list;
}
