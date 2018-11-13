package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Presentation;
import com.mitchellbosecke.benchmark.model.Stock;
import com.mitchellbosecke.benchmark.templates.PresentationsKotlin;
import com.mitchellbosecke.benchmark.templates.StocksKotlin;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class KotlinHtml extends BaseBenchmark {

    private List<Stock> stocks = Stock.dummyItems();
    private final Collection<Presentation> presentations = Presentation.dummyItems();

    @Benchmark
    public String stocks(){
        return StocksKotlin.Companion.stocksTemplate(stocks);
    }

    @Benchmark
    public String presentations() throws IOException {
        return PresentationsKotlin.Companion.presentationsTemplate(presentations);
    }
}
