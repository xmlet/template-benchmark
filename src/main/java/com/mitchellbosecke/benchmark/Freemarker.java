package com.mitchellbosecke.benchmark;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Freemarker extends BaseBenchmark {

    private Map<String, Object> context;

    private Template stocksTemplate;
    private Template presentationsTemplate;

    @Setup
    public void setup() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
        stocksTemplate = configuration.getTemplate("templates/stocks.freemarker.html");
        presentationsTemplate = configuration.getTemplate("templates/presentations.freemarker.html");
        this.context = getContext();
    }
    @Benchmark
    public String stocks() throws TemplateException, IOException {
        Writer writer = new StringWriter();
        stocksTemplate.process(context, writer);
        return writer.toString();
    }

    @Benchmark
    public String presentations() throws TemplateException, IOException {
        Writer writer = new StringWriter();
        presentationsTemplate.process(context, writer);
        return writer.toString();
    }

}
