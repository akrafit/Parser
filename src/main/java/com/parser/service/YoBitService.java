package com.parser.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parser.dtoYoBit.TickerDto;
import com.parser.dtoYoBit.YoBitInfoDto;
import com.parser.entityYoBit.PairYobit;
import com.parser.entityYoBit.TickerYobit;
import com.parser.repo.PairYobitRepository;
import com.parser.repo.TickerYobitRepository;
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
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class YoBitService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Type listType = new TypeToken<Map<String, TickerDto>>(){}.getType();
    private final PairYobitRepository pairYobitRepository;
    private final TickerYobitRepository tickerYobitRepository;

    public YoBitInfoDto getYoBitPair(){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://yobit.net/api/3/info"))
                    //.headers("content-type", "application/json")
                    //.POST(HttpRequest.BodyPublishers.noBody())
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), YoBitInfoDto.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //@Scheduled(cron = "*/20 * * * * *")
    public void loadPairsInDataBase(){
        System.out.println("есть");
        ArrayList<PairYobit> pairYobitArrayList = new ArrayList<>();
        YoBitInfoDto yoBitInfoDto = getYoBitPair();
        System.out.println("есть2");
        Set<String> keySetPair = yoBitInfoDto.getPairs().keySet();
        keySetPair.forEach(s -> pairYobitArrayList.add(new PairYobit(s, yoBitInfoDto.getPairs().get(s))));
        pairYobitRepository.saveAll(pairYobitArrayList);
        System.out.println("ok");
    }

    public Map<String, TickerDto> getYoBitTicker(String pairs){
        try {
            HttpRequest request  = HttpRequest.newBuilder()
                    .uri(new URI("https://yobit.net/api/3/ticker/" + pairs))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), listType);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //@Scheduled(cron = "*/20 * * * * *")
    public void loadTickerInDataBase(){
        ArrayList<TickerYobit> tickerYobitArrayList = new ArrayList<>();
        Map<String, TickerDto> tickerDtoMap = getYoBitTicker("usdt_btc");
        Set<String> pairNameSet = tickerDtoMap.keySet();
        pairNameSet.forEach(s -> {
            tickerYobitArrayList.add(new TickerYobit(s, tickerDtoMap.get(s)));
        });
        tickerYobitRepository.saveAll(tickerYobitArrayList);
    }
}
