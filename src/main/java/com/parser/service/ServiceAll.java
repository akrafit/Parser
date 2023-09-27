package com.parser.service;

import com.parser.dtoBinance.TickerBinanceDto;
import com.parser.dtoBybit.SymbolDtoBybit;
import com.parser.dtoPoloniex.TickerDtoPoloniex;
import com.parser.dtoYoBit.TickerDto;
import com.parser.entityAll.PairEntity;
import com.parser.entityAll.TickerForExcel;
import com.parser.entityBinance.PairBinance;
import com.parser.entityBybit.PairBybit;
import com.parser.entityPoloniex.PairPoloniex;
import com.parser.entityYoBit.PairYobit;
import com.parser.repo.*;
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
    private final PoloniexService poloniexService;
    private final PairPoloniexRepository pairPoloniexRepository;
    private List<PairEntity> pairEntityList = new ArrayList<>();
    private List<TickerForExcel> tickerForExcelList = new ArrayList<>();

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

    //@Scheduled(cron = "*/20 * * * * *")
    @Scheduled(cron = "0 * * * * *")
    public void loadPriceAll() {
        if (pairEntityList.size() == 0) {
            log.info("загрузка pairEntity");
            pairEntityList = pairRepository.findAll();

            checkPair();
        }
        pairEntityList.forEach(pairEntity -> {
            if (pairEntity.getBinanceNameId() != null) {
                TickerBinanceDto tickerBinance = binanceService.getTickerDataFromBinance(pairEntity.getBinanceNameId());
                //log.info("ok binance" + tickerBinance.getLastPrice());
                pairEntity.setBinancePrice(tickerBinance.getLastPrice());
                pairEntity.setBinanceVolume(tickerBinance.getVolume());
            }

//            if (pairEntity.getYobitNameId() != null) {
//                TickerDto tickerDto = yoBitService.getYoBitTicker(pairEntity.getYobitNameId()).get(pairEntity.getYobitNameId());
//                //log.info("ok yobit" + tickerDto.getLast());
//                pairEntity.setYobitPrice(tickerDto.getLast());
//                pairEntity.setYobitVolume(tickerDto.getVol());
//            }
            if (pairEntity.getBybitNameId() != null) {
                SymbolDtoBybit symbolDtoBybit = bybitService.getTickerDataFromBybit(pairEntity.getBybitNameId()).getResult().getList().get(0);
                //log.info("ok bybit" + symbolDtoBybit.getLastPrice());
                pairEntity.setBybitPrice(symbolDtoBybit.getLastPrice());
                pairEntity.setByBitVolume(symbolDtoBybit.getVolume24h());
            }
            if (pairEntity.getPoloniexNameId() != null) {
                TickerDtoPoloniex tickerDtoPoloniex = poloniexService.getTickerDataFromBinance(pairEntity.getPoloniexNameId());
                //log.info("poloniex " + tickerDtoPoloniex.getPrice());
                pairEntity.setPoloniexPrice(tickerDtoPoloniex.getPrice());
                pairEntity.setPoloniexVolume("1000");
            }
            checkCoinAndPrintConsole(pairEntity);
        });
        //System.out.println("save");

        pairRepository.saveAll(pairEntityList);
        //printPairList();
    }

    private void checkPair() {
        pairEntityList.forEach(pairEntity -> {
            if (pairEntity.getBinanceNameId() == null) {
                List<PairBinance> pairBinanceList = pairBinanceRepository.findByCodeIs(pairEntity.getCode());
                if (pairBinanceList.size() > 0) {
                    pairEntity.setBinanceNameId(pairBinanceList.get(0).getSymbol());
                }
            }
            if (pairEntity.getYobitNameId() == null) {
                List<PairYobit> pairYobitList = pairYobitRepository.findByCodeIs(pairEntity.getCode());
                if (pairYobitList.size() > 0) {
                    pairEntity.setYobitNameId(pairYobitList.get(0).getPair());
                }

            }
            if (pairEntity.getBybitNameId() == null) {
                List<PairBybit> pairBybitList = pairBybitRepository.findByCodeIs(pairEntity.getCode());
                if (pairBybitList.size() > 0) {
                    pairEntity.setBybitNameId(pairBybitList.get(0).getSymbol());
                }

            }
            if (pairEntity.getPoloniexNameId() == null) {
                List<PairPoloniex> pairPoloniexList = pairPoloniexRepository.findByCodeIs(pairEntity.getCode());
                if (pairPoloniexList.size() > 0) {
                    pairEntity.setPoloniexNameId(pairPoloniexList.get(0).getSymbol());
                }
            }
        });

    }

    private void checkCoinAndPrintConsole(PairEntity pairEntity) {
        double binancePrice = 0.0;
        double yobitPrice = 0.0;
        double bybitPrice = 0.0;
        double poloniexPrice = 0.0;
        double percentBinanceBybit = 0.0;
        double percentBinanceYobit = 0.0;
        double percentBinancePoloniex = 0.0;
        if (pairEntity.getBinancePrice() != null) {
            binancePrice = Double.parseDouble(pairEntity.getBinancePrice());
            pairEntity.setBinanceDoublePrice(binancePrice);
        }
        if (pairEntity.getBybitPrice() != null) {
            bybitPrice = Double.parseDouble(pairEntity.getBybitPrice());
            pairEntity.setBybitDoublePrice(bybitPrice);
        }
//        if (pairEntity.getYobitPrice() != null) {
//            yobitPrice = Double.parseDouble(pairEntity.getYobitPrice());
//            pairEntity.setYobitDoublePrice(yobitPrice);
//        }
        if (pairEntity.getPoloniexPrice() != null) {
            poloniexPrice = Double.parseDouble(pairEntity.getPoloniexPrice());
            pairEntity.setPoloniexDoublePrice(poloniexPrice);
        }
        if (binancePrice > 0.0 & bybitPrice > 0.0) {
            percentBinanceBybit = Math.abs((binancePrice - bybitPrice) * 100 / binancePrice);
        }
//        if (binancePrice > 0.0 & yobitPrice > 0.0) {
//            percentBinanceYobit = Math.abs((binancePrice - yobitPrice) * 100 / binancePrice);
//        }
        if (poloniexPrice > 0.0 & binancePrice > 0.0) {
            percentBinancePoloniex = Math.abs((binancePrice - poloniexPrice) * 100 / binancePrice);
        }

        if (percentBinanceBybit > 1
               // | percentBinanceYobit > 1
                | percentBinancePoloniex > 1) {
            log.info(pairEntity.getCode() +
                    " " + String.format("%.3f", percentBinanceBybit) +
                   // " " + String.format("%.3f", percentBinanceYobit) +
                    " " + String.format("%.3f", percentBinancePoloniex) +
                    " | " + binancePrice +
                    " " + yobitPrice +
                    " " + bybitPrice +
                    " " + poloniexPrice +
                    " | bin - " + pairEntity.getBinanceVolume() + " by - " + pairEntity.getByBitVolume() );
                    //+ " yo - " + pairEntity.getYobitVolume());
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
