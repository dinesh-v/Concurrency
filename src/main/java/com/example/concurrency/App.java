package com.example.concurrency;

import java.util.List;

public class App {
    public static void main(String[] args) {
        String fileName = "index.txt";
        List<String> tickers = ReadFile.readTicker(fileName);
        new Thread(() -> StockReader.getSequential(tickers)).start();
        new Thread(() -> StockReader.getParallelWithoutExecutor(tickers)).start();
        new Thread(() -> StockReader.getParallelWithExecutor(tickers)).start();
        System.out.println("Main thread completed " + Thread.currentThread().getName());
        /*StockReader.getSequential(tickers);*/
        /*StockReader.getParallel(tickers);*/
    }
}
