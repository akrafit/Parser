package com.parser.service;

import com.parser.dtoBinance.TickerBinanceDto;
import com.parser.dtoBybit.SymbolDtoBybit;
import com.parser.dtoYoBit.TickerDto;
import com.parser.entityAll.PairEntity;
import com.parser.entityBinance.PairBinance;
import com.parser.entityYoBit.PairYobit;
import com.parser.repo.PairBinanceRepository;
import com.parser.repo.PairBybitRepository;
import com.parser.repo.PairRepository;
import com.parser.repo.PairYobitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class ServiceAll {
    private final PairBinanceRepository pairBinanceRepository;
    private final PairYobitRepository pairYobitRepository;

    private final PairBybitRepository pairBybitRepository;
    private final PairRepository pairRepository;
    private final BinanceService binanceService;
    private final YoBitService yoBitService;
    private final BybitService bybitService;
    private List<PairEntity> pairEntityList = new ArrayList<>();

    //@Scheduled(cron = "*/20 * * * * *")
    public void loadTickerInDataBase() {
        List<PairYobit> pairYobitList = pairYobitRepository.getPairYobitByCode();
        List<PairBinance> pairBinanceList = pairBinanceRepository.getPairBinanceByCode();
        //List<PairBybit> pairBybitList =
        Map<String, PairBinance> binanceMap = new HashMap<>();
        Map<String, PairYobit> yobitMap = new HashMap<>();
        pairYobitList.forEach(pairYobit -> {
            yobitMap.put(pairYobit.getCode(), pairYobit);
        });
        pairBinanceList.forEach(pairBinance -> {
            binanceMap.put(pairBinance.getCode(), pairBinance);
        });
        List<PairEntity> pairEntityList = new ArrayList<>();
        Set<String> stringSet = binanceMap.keySet();
        stringSet.forEach(s -> {
            if (yobitMap.containsKey(s)) {
                //pairEntityList.add(new PairEntity(binanceMap.get(s), yobitMap.get(s)));
            }
        });
        pairRepository.saveAll(pairEntityList);
        System.out.println(pairEntityList.size());
    }

    @Scheduled(cron = "*/20 * * * * *")
    public void loadPriceAll() {
        if (pairEntityList.size() == 0) {
            pairEntityList = pairRepository.findAll();
        }
        pairEntityList.forEach(pairEntity -> {
            if (pairEntity.getBinanceNameId() != null) {
                TickerBinanceDto tickerBinance = binanceService.getTickerDataFromBinance(pairEntity.getBinanceNameId());
                //log.info("ok binance" + tickerBinance.getLastPrice());
                pairEntity.setBinancePrice(tickerBinance.getLastPrice());
                pairEntity.setBinanceVolume(tickerBinance.getVolume());
            }

            if (pairEntity.getYobitNameId() != null) {
                TickerDto tickerDto = yoBitService.getYoBitTicker(pairEntity.getYobitNameId()).get(pairEntity.getYobitNameId());
                //log.info("ok yobit" + tickerDto.getLast());
                pairEntity.setYobitPrice(tickerDto.getLast());
                pairEntity.setYobitVolume(tickerDto.getVol());
            }
            if (pairEntity.getBybitNameId() != null) {
                SymbolDtoBybit symbolDtoBybit = bybitService.getTickerDataFromBybit(pairEntity.getBybitNameId()).getResult().getList().get(0);
                //log.info("ok bybit" + symbolDtoBybit.getLastPrice());
                pairEntity.setBybitPrice(symbolDtoBybit.getLastPrice());
                pairEntity.setByBitVolume(symbolDtoBybit.getVolume24h());
            }
            checkCoin(pairEntity);
        });

        pairRepository.saveAll(pairEntityList);
        //printPairList();
    }

    private void checkCoin(PairEntity pairEntity) {
        double binancePrice = 0.0;
        double yobitPrice = 0.0;
        double bybitPrice = 0.0;
        double percentBinanceBybit = 0.0;
        double percentBinanceYobit = 0.0;
        double percentYobitBybit = 0.0;
        if (pairEntity.getBinancePrice() != null) {
            binancePrice = Double.parseDouble(pairEntity.getBinancePrice());
        }
        if (pairEntity.getBybitPrice() != null) {
            bybitPrice = Double.parseDouble(pairEntity.getBybitPrice());
        }
        if (pairEntity.getYobitPrice() != null) {
            yobitPrice = Double.parseDouble(pairEntity.getYobitPrice());
        }
        if (binancePrice > 0.0 & bybitPrice > 0.0) {
            percentBinanceBybit = (binancePrice - bybitPrice) * 100 / binancePrice;
        }
        if (binancePrice > 0.0 & yobitPrice > 0.0) {
            percentBinanceYobit = (binancePrice - yobitPrice) * 100 / binancePrice;
        }
        if (yobitPrice > 0.0 & bybitPrice > 0.0) {
            percentYobitBybit = (yobitPrice - bybitPrice) * 100 / yobitPrice;
        }
        if (percentBinanceBybit > 1 | percentBinanceYobit > 1 | percentYobitBybit > 1) {
            System.out.println(pairEntity.getCode() +
                    " " + String.format("%.3f", percentBinanceBybit) +
                    " " + String.format("%.3f", percentBinanceYobit) +
                    " " + String.format("%.3f", percentYobitBybit) +
                    "  bin - " + pairEntity.getBinanceVolume() + " by - " + pairEntity.getByBitVolume() + " yo - " + pairEntity.getYobitVolume());
        }
    }

//    private void printPairList() {
//        pairEntityList.forEach(pairEntity -> {
//
//            String sb = pairEntity.getCode() + pairEntity.getBinancePrice() + " - " +
//                    Double.parseDouble(pairEntity.getBinancePrice()) * 100 / Double.parseDouble(pairEntity.getYobitPrice()) +
//                    " vol -" +
//                    pairEntity.getBinanceVolume() +
//                    " - " +
//                    pairEntity.getYobitVolume();
//                System.out.println(sb);
//
//
//        });
//    }
}
