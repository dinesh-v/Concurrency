package com.example.concurrency;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

class StockReader {
    static {
        YahooFinance.logger.setUseParentHandlers(false);
    }

    static void getSequential(List<String> tickers) {
        long start = System.currentTimeMillis();
        tickers.forEach(ticker -> {
            Stock stock;
            try {
                stock = YahooFinance.get(ticker);
                /*System.out.println(ticker + " --> " + stock.getQuote().getPrice());*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time in getSequential is " + formatter.format((end - start) / 1000d) + " seconds");
    }

    static void getParallel(List<String> tickers) {
        long start = System.currentTimeMillis();
        tickers.parallelStream().forEach(ticker -> {
            Stock stock;
            try {
                stock = YahooFinance.get(ticker);
                /*System.out.println(ticker + " --> " + stock.getQuote().getPrice());*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time in getParallel is " + formatter.format((end - start) / 1000d) + " seconds");
    }
}
