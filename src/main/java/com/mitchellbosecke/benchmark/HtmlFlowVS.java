package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Presentation;
import com.mitchellbosecke.benchmark.model.Stock;
import com.mitchellbosecke.benchmark.templates.PresentationsHtmlFlow;
import com.mitchellbosecke.benchmark.templates.PresentationsHtmlFlowVs;
import com.mitchellbosecke.benchmark.templates.StocksHtmlFlowVS;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.util.List;

public class HtmlFlowVS extends BaseBenchmark {

    private List<Stock> stocks;
    private Iterable<Presentation> presentations;

    @Setup
    public void setup() throws IOException {
        this.stocks = Stock.dummyItems();
        this.presentations = Presentation.dummyItems();
    }

    @Benchmark
    public String stocks() throws IOException {
        return StocksHtmlFlowVS.INSTANCE.getView().render(stocks);
    }

    @Benchmark
    public String presentations() throws IOException {
        return PresentationsHtmlFlowVs.INSTANCE.getView().render(presentations);
    }
}
