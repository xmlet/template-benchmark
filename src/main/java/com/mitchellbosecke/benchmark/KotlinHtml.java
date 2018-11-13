package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Stock;
import com.mitchellbosecke.benchmark.templates.StocksKotlin;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;

public class KotlinHtml extends BaseBenchmark {

    private List<Stock> stocks = Stock.dummyItems();

    @Benchmark
    public String stocks(){
        return StocksKotlin.Companion.stocksTemplate(stocks);
    }
}
