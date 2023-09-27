package com.parser.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parser.dtoPoloniex.ExchangeInfoPolDto;
import com.parser.dtoPoloniex.TickerDtoPoloniex;
import com.parser.entityPoloniex.PairPoloniex;
import com.parser.repo.PairPoloniexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
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
public class PoloniexService {
    private final Type listType = new TypeToken<List<ExchangeInfoPolDto>>() {
    }.getType();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final PairPoloniexRepository pairPoloniexRepository;

    public List<ExchangeInfoPolDto> getExchangeInfo() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.poloniex.com/markets"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), listType);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //@Scheduled(cron = "*/20 * * * * *")
    public void loadPairsFromPoloniexInDataBase() {
        System.out.println("запуск");
        List<ExchangeInfoPolDto> exchangeInfoPolDtoList = getExchangeInfo();
        List<PairPoloniex> pairPoloniexList = new ArrayList<>();
        //List<SymbolDto> symbolDtoList = exchangeInfoBinDto.getSymbols();
        exchangeInfoPolDtoList.forEach(exchangeInfoPolDto -> {
            pairPoloniexList.add(new PairPoloniex(exchangeInfoPolDto));
        });
        pairPoloniexRepository.saveAll(pairPoloniexList);
        System.out.println(exchangeInfoPolDtoList.size());
        System.out.println("закончили");
    }

    public TickerDtoPoloniex getTickerDataFromBinance(String pairs) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.poloniex.com/markets/" + pairs + "/price"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), TickerDtoPoloniex.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    //@Scheduled(cron = "*/20 * * * * *")
//    public void loadTickersFromBinance() {
//        System.out.println("запуск");
//        TickerBinanceDto tickerBinanceDto = getTickerDataFromBinance("BNBBTC");
//        tickerBinanceRepository.save(new TickerBinance(tickerBinanceDto));
//        System.out.println(tickerBinanceDto);
//        System.out.println("закончили");
//    }
}
