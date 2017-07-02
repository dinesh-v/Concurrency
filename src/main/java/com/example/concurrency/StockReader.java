package com.example.concurrency;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class StockReader {
    static {
        YahooFinance.logger.setUseParentHandlers(false);
    }

    static List<Stock> getSequential(List<String> tickers) {
        if (tickers.size() == 0) return null;
        List<Stock> stocks = new LinkedList<>();
        long start = System.currentTimeMillis();
        tickers.forEach(ticker -> {
            Stock stock;
            try {
                stock = YahooFinance.get(ticker);
                stocks.add(stock);
                /*System.out.println(ticker + " --> " + stock.getQuote().getPrice());*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time in getSequential is " + formatter.format((end - start) / 1000d) + " seconds");
        return stocks;
    }

    static List<Stock> getParallel(List<String> tickers) {
        if (tickers.size() == 0) return null;
        List<Stock> stocks = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);
        long start = System.currentTimeMillis();
        tickers.parallelStream().forEach(ticker -> {
            Stock stock;
            Future<Stock> future = executorService.submit(() -> YahooFinance.get(ticker));
            try {
                stock = future.get();
                stocks.add(stock);
                /*System.out.println(ticker + " --> " + stock.getQuote().getPrice());*/
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time in getParallel is " + formatter.format((end - start) / 1000d) + " seconds");
        return stocks;
    }

    static List<Stock> getParallelWithoutExecutor(List<String> tickers) {
        if (tickers.size() == 0) return null;
        List<Stock> stocks = new LinkedList<>();
        long start = System.currentTimeMillis();
        tickers.parallelStream().forEach(ticker -> {
            Stock stock;
            try {
                stock = YahooFinance.get(ticker);
                stocks.add(stock);
                /*System.out.println(ticker + " --> " + stock.getQuote().getPrice());*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time in getParallelWithoutExecutor is " + formatter.format((end - start) / 1000d) + " seconds");
        return stocks;
    }
}
