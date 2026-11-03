package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Presentation;
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
    private Iterable<Presentation> presentations;

    @Setup
    public void setup() {
        items = Stock.dummyItems();
        this.presentations = Presentation.dummyItems();
        templateEngine = TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Plain);
        templateEngine.prepareForRendering("stocks.jte");
        templateEngine.prepareForRendering("presentations.jte");
    }

    @Benchmark
    public String presentations() {
        StringOutput output = new StringOutput();
        templateEngine.render("presentations.jte", presentations, output);
        return output.toString();
    }

    @Benchmark
    public String stocks() {
        StringOutput output = new StringOutput();
        templateEngine.render("stocks.jte", items, output);
        return output.toString();
    }
}
