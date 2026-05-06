package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Stock;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.nio.file.Path;
import java.util.List;

/**
 * Benchmark for jte templates.
 * <p>
 * <a href="https://github.com/casid/jte">Find jte on GitHub</a>
 *
 * @author casid
 */
public class Jte extends BaseBenchmark {
    private TemplateEngine templateEngine;
    private List<Stock> items;

    @Setup
    public void setup() {
        items = Stock.dummyItems();

        templateEngine = TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Plain);
        templateEngine.prepareForRendering("stocks.jte");
    }

    @Benchmark
    public String stocks() {
        StringOutput output = new StringOutput();
        templateEngine.render("stocks.jte", items, output);
        return output.toString();
    }
}
