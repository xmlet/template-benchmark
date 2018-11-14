package com.mitchellbosecke.benchmark;

import java.io.IOException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.jknack.handlebars.Handlebars.SafeString;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.mitchellbosecke.benchmark.model.Stock;

public class Handlebars extends BaseBenchmark {

  private Object context;

    private Template stocksTemplate;
    private Template presentationsTemplate;

    @Setup
    public void setup() throws IOException {
        stocksTemplate = new com.github.jknack.handlebars.Handlebars(new ClassPathTemplateLoader("/", ".html"))
                .registerHelper("minus", new Helper<Stock>() {
                    @Override
                    public CharSequence apply(final Stock stock, final Options options)
                            throws IOException {
                        return stock.getChange() < 0 ? new SafeString("class=\"minus\"") : null;
                    }
                }).compile("templates/stocks.hbs");

        presentationsTemplate = new com.github.jknack.handlebars.Handlebars(
                new ClassPathTemplateLoader("/", ".html")).
                compile("templates/presentations.hbs");
        this.context = getContext();
    }

    @Benchmark
    public String stocks() throws IOException {
        return stocksTemplate.apply(context);
    }

    @Benchmark
    public String presentations() throws IOException {
        return presentationsTemplate.apply(context);
    }

}
