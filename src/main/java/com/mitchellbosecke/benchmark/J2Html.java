package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Presentation;
import com.mitchellbosecke.benchmark.model.Stock;
import j2html.attributes.Attribute;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.List;

import static j2html.TagCreator.*;
import static j2html.TagCreator.td;
import static java.util.stream.Collectors.joining;

public class J2Html extends BaseBenchmark {

    private static final String css;

    static {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(J2Html.class.getResourceAsStream("/templates/stocks.css")))) {
            css = in.lines().collect(joining());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String HEADER = "<!DOCTYPE html>";
    private List<Stock> stocks = (List<Stock>) Stock.dummyItems();
    private Collection<Presentation> presentations = Presentation.dummyItems();

    @Benchmark
    public String stocks() {
        final int[] index = {0};

        return HEADER + html(
                head(
                        title("Stock Prices"),
                        meta().attr(new Attribute("http-equiv", "Content-Type")).withContent("text/html; charset=UTF-8"),
                        meta().attr(new Attribute("http-equiv", "Content-Style-Type")).withContent("text/css"),
                        meta().attr(new Attribute("http-equiv", "Content-Script-Type")).withContent("text/javascript"),
                        link().withRel("shortcut icon").withHref("/images/favicon.ico"),
                        link().withRel("Stylesheet").withType("text/css").withHref("/css/style.css").attr(new Attribute("media", "all")),
                        script().withType("text/javascript").withSrc("/js/util.js"),
                        style(css).withType("text/css")
                ),
                body(
                        h1().withText("Stock Prices"),
                        table(
                                thead(
                                        tr(
                                                th(text("#")),
                                                th(text("symbol")),
                                                th(text("name")),
                                                th(text("price")),
                                                th(text("change")),
                                                th(text("ratio"))
                                        )
                                ),
                                tbody(
                                        each(stocks, (Stock stock) ->
                                                tr(
                                                        td().withText(String.valueOf(index[0])),
                                                        td(a().withHref("/stocks/" + stock.getSymbol()).withText(stock.getSymbol())),
                                                        td(a().withHref(stock.getUrl()).withText(stock.getName())),
                                                        td(strong().withText(String.valueOf(stock.getPrice()))),
                                                        stock.getChange() < 0 ? td().withClass("minus").withText(String.valueOf(stock.getChange())) : td().withText(String.valueOf(stock.getChange())),
                                                        stock.getRatio() < 0 ? td().withClass("minus").withText(String.valueOf(stock.getRatio())) : td().withText(String.valueOf(stock.getRatio()))
                                                ).withClass(index[0]++ % 2 == 0 ? "even" : "odd")
                                        )
                                )
                        )
                )
        ).renderFormatted();
    }

    @Benchmark
    public String presentations() {
        return HEADER + html(
                head(
                        meta().withCharset("utf-8"),
                        meta().withName("viewport").withContent("width=device-width, initial-scale=1.0"),
                        meta().attr(new Attribute("http-equiv", "Content-Language")).withContent("IE=Edge"),
                        title("JFall 2013 Presentations - htmlApi"),
                        link().withRel("Stylesheet").withHref("/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css").attr(new Attribute("media", "screen"))
                ),
                body(
                        div(
                                div(
                                        h1("JFall 2013 Presentations - htmlApi")
                                ).withClass("page-header"),
                                each(presentations, (Presentation presentation) ->
                                        div(
                                                div(
                                                        h3(presentation.getTitle() + " - " + presentation.getSpeakerName()).withClass("panel-title")
                                                ).withClass("panel-heading"),
                                                div(presentation.getSummary()).withClass("panel-body")
                                        ).withClass("panel panel-default")
                                )
                        ).withClass("container"),
                        script().withSrc("/webjars/jquery/3.1.1/jquery.min.js"),
                        script().withSrc("/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js")
                )
        ).renderFormatted();
    }
}
