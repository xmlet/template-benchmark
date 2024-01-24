package com.mitchellbosecke.benchmark;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class Pebble extends BaseBenchmark {

    private Map<String, Object> context;

    private PebbleTemplate stocksTemplate;
    private PebbleTemplate presentationsTemplate;

    @Setup
    public void setup() throws PebbleException {
        PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(false).build();
        stocksTemplate = engine.getTemplate("templates/stocks.pebble.html");
        presentationsTemplate = engine.getTemplate("templates/presentations.pebble.html");
        this.context = getContext();
    }

    @Benchmark
    public String stocks() throws PebbleException, IOException {
        StringWriter writer = new StringWriter();
        stocksTemplate.evaluate(writer, context);
        return writer.toString();
    }

    @Benchmark
    public String presentations() throws PebbleException, IOException {
        StringWriter writer = new StringWriter();
        presentationsTemplate.evaluate(writer, context);
        return writer.toString();
    }

}
