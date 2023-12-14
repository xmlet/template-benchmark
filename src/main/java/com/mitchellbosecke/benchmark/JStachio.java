package com.mitchellbosecke.benchmark;

import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.mitchellbosecke.benchmark.model.Presentation;
import com.mitchellbosecke.benchmark.model.Stock;

import io.jstach.jstache.JStache;
import io.jstach.jstache.JStacheConfig;
import io.jstach.jstache.JStacheFlags;
import io.jstach.jstache.JStacheFlags.Flag;
import io.jstach.jstache.JStacheLambda;
import io.jstach.jstachio.escapers.PlainText;

public class JStachio extends BaseBenchmark {

    // Let us cheat like rocker and jte
    private static final ThreadLocal<StringBuilder> buffer = ThreadLocal.withInitial(() -> new StringBuilder(1024 * 8));
    
    private List<Stock> items;
    private Iterable<Presentation> presentations;
    private StocksModel stocksModel;
    private PresentationsModel presentationsModel;
    private JStachioStocksTemplate stocksTemplate;
    private JStachioPresentationsTemplate presentationsTemplate;
    
    @Setup
    public void setup() {
        items = Stock.dummyItems();
        stocksModel = new StocksModel(items);
        stocksTemplate = JStachioStocksTemplate.of();
        presentations = Presentation.dummyItems();
        presentationsModel = new PresentationsModel(presentations);
        presentationsTemplate = JStachioPresentationsTemplate.of();
    }

    @Benchmark
    public String stocks() {
        StringBuilder sb = buffer.get();
        sb.setLength(0);
        return stocksTemplate.execute(stocksModel, sb).toString();
    }
    
    @Benchmark
    public String presentations() {
        StringBuilder sb = buffer.get();
        sb.setLength(0);
        return presentationsTemplate.execute(presentationsModel, sb).toString();
    }

    @JStache(path = "templates/stocks.jstachio.html", 
            name="JStachioStocksTemplate")
    @JStacheConfig(contentType=PlainText.class)
    @JStacheFlags(flags = {Flag.NO_NULL_CHECKING})
    public static class StocksModel {

        public final List<Stock> items;

        public StocksModel(List<Stock> items) {
            this.items = items;
        }
        
        @JStacheLambda
        public boolean isPositive(Stock stock) {
            return stock.getChange() > 0;
        }
        
        @JStacheLambda
        public boolean isEven(int index) {
            return index % 2 == 0;
        }

    }

    @JStache(path = "templates/presentations.jstachio.html", 
            name="JStachioPresentationsTemplate")
    @JStacheConfig(contentType=PlainText.class)
    @JStacheFlags(flags = {Flag.NO_NULL_CHECKING})
    public static class PresentationsModel{

        public final Iterable<Presentation> presentationItems;

        public PresentationsModel(Iterable<Presentation> presentationItems) {
            this.presentationItems = presentationItems;
        }

    }
}
