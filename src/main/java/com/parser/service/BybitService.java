package com.parser.service;

import com.google.gson.Gson;
import com.parser.dtoBinance.ExchangeInfoBinDto;
import com.parser.dtoBinance.SymbolDto;
import com.parser.dtoBinance.TickerBinanceDto;
import com.parser.dtoBybit.ExchangeInfoBybitDto;
import com.parser.dtoBybit.SymbolDtoBybit;
import com.parser.entityBinance.PairBinance;
import com.parser.entityBinance.TickerBinance;
import com.parser.entityBybit.PairBybit;
import com.parser.entityBybit.TickerBybit;
import com.parser.repo.PairBybitRepository;
import com.parser.repo.TickerBybitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class BybitService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final PairBybitRepository pairBybitRepository;
    private final TickerBybitRepository tickerBybitRepository;
    public ExchangeInfoBybitDto getExchangeInfoBybit(){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.bybit.com/v5/market/instruments-info?category=spot"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(),ExchangeInfoBybitDto.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //@Scheduled(cron = "*/20 * * * * *")
    public void loadPairsInDataBase(){
        log.info("запуск");
        ExchangeInfoBybitDto exchangeInfoBinDto = getExchangeInfoBybit();
        List<PairBybit> pairBybitList = new ArrayList<>();
        List<SymbolDtoBybit> symbolDtoList = exchangeInfoBinDto.getResult().list;
        symbolDtoList.forEach(symbolDto -> {
            pairBybitList.add(new PairBybit(symbolDto));
            //System.out.println(symbolDto.symbol);
        });
        pairBybitRepository.saveAll(pairBybitList);
        log.info("закончили");
    }

    public ExchangeInfoBybitDto getTickerDataFromBybit(String pairs) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.bybit.com/v5/market/tickers?category=spot&symbol=" + pairs))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), ExchangeInfoBybitDto.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //@Scheduled(cron = "*/20 * * * * *")
    public void loadTickersFromBybit() {
        ExchangeInfoBybitDto exchangeInfoBybitDto = getTickerDataFromBybit("BTCUSDT");
        tickerBybitRepository.save(new TickerBybit(exchangeInfoBybitDto.getResult().getList().get(0)));
    }
}
