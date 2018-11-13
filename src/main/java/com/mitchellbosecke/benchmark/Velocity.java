package com.mitchellbosecke.benchmark;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class Velocity extends BaseBenchmark {

    private VelocityContext context;

    private Template stocksTemplate;
    private Template presentationsTemplate;

    @Setup
    public void setup() {
        Properties configuration = new Properties();
        configuration.setProperty("resource.loader", "class");
        configuration.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        VelocityEngine engine = new VelocityEngine(configuration);
        context = new VelocityContext(getContext());
        stocksTemplate = engine.getTemplate("templates/stocks.velocity.html", "UTF-8");
        presentationsTemplate = engine.getTemplate("templates/presentations.velocity.html", "UTF-8");
    }

    @Benchmark
    public String stocks() {
        Writer writer = new StringWriter();
        stocksTemplate.merge(context, writer);
        return writer.toString();
    }

    @Benchmark
    public String presentations() {
        Writer writer = new StringWriter();
        presentationsTemplate.merge(context, writer);
        return writer.toString();
    }

}
