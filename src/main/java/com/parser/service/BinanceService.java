package com.parser.service;

import com.google.gson.Gson;
import com.parser.dtoBinance.SymbolDto;
import com.parser.entityBinance.PairBinance;
import com.parser.entityBinance.TickerBinance;
import com.parser.repo.PairBinanceRepository;
import com.parser.dtoBinance.ExchangeInfoBinDto;
import com.parser.dtoBinance.TickerBinanceDto;
import com.parser.repo.TickerBinanceRepository;
import lombok.RequiredArgsConstructor;
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
public class BinanceService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final TickerBinanceRepository tickerBinanceRepository;
    private final PairBinanceRepository pairBinanceRepository;

    public ExchangeInfoBinDto getExchangeInfo() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.binance.com/api/v3/exchangeInfo"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), ExchangeInfoBinDto.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //@Scheduled(cron = "*/20 * * * * *")
    public void loadPairsFromBinanceInDataBase() {
        System.out.println("запуск");
        ExchangeInfoBinDto exchangeInfoBinDto = getExchangeInfo();
        List<PairBinance> pairBinanceList = new ArrayList<>();
        List<SymbolDto> symbolDtoList = exchangeInfoBinDto.getSymbols();
        symbolDtoList.forEach(symbolDto -> {
            pairBinanceList.add(new PairBinance(symbolDto));
        });
        pairBinanceRepository.saveAll(pairBinanceList);
        System.out.println("закончили");
    }

    public TickerBinanceDto getTickerDataFromBinance(String pairs) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.binance.com/api/v3/ticker?symbol=" + pairs))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), TickerBinanceDto.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //@Scheduled(cron = "*/20 * * * * *")
    public void loadTickersFromBinance() {
        System.out.println("запуск");
        TickerBinanceDto tickerBinanceDto = getTickerDataFromBinance("BNBBTC");
        tickerBinanceRepository.save(new TickerBinance(tickerBinanceDto));
        System.out.println(tickerBinanceDto);
        System.out.println("закончили");
    }
}
