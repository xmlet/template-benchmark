package com.mitchellbosecke.benchmark.templates;

import com.mitchellbosecke.benchmark.model.Stock;
import htmlflow.HtmlFlow;
import htmlflow.HtmlPage;
import htmlflow.HtmlView;
import org.xmlet.htmlapifaster.EnumHttpEquivType;
import org.xmlet.htmlapifaster.EnumMediaType;
import org.xmlet.htmlapifaster.EnumRelType;
import org.xmlet.htmlapifaster.EnumTypeContentType;
import org.xmlet.htmlapifaster.EnumTypeScriptType;
import templates.stocks;

import java.util.List;
import java.util.stream.IntStream;

public class StocksHtmlFlow {

    public static HtmlView view = HtmlFlow.view(StocksHtmlFlow::templateStocks);

    private static void templateStocks(HtmlPage view) {
        view
            .html()
                .head()
                    .title().text("Stock Prices").__()
                    .meta()
                        .attrHttpEquiv(EnumHttpEquivType.CONTENT_TYPE)
                        .attrContent("text/html; charset=UTF-8")
                    .__()
                    .meta()
                        .addAttr("http-equiv", "Content-Style-Type")
                        .attrContent("text/CSS")
                    .__()
                    .meta()
                        .addAttr("http-equiv", "Content-Script-Type")
                        .attrContent("text/javascript")
                    .__()
                    .link()
                        .addAttr("rel", "shortcut icon")
                        .attrHref("/images/favicon.ico")
                    .__()
                    .link()
                        .attrRel(EnumRelType.STYLESHEET)
                        .attrType(EnumTypeContentType.TEXT_CSS)
                        .attrHref("/CSS/style.CSS")
                        .attrMedia(EnumMediaType.ALL)
                    .__()
                    .script()
                        .attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
                        .attrSrc("/js/util.js")
                    .__()
                    .style()
                        .attrType(EnumTypeContentType.TEXT_CSS)
                        .text(STOCKS_CSS)
                    .__()
                .__() // head
                .body()
                    .h1().text("Stock Prices").__()
                    .table()
                        .thead()
                            .tr()
                                .th().text("#").__()
                                .th().text("symbol").__()
                                .th().text("name").__()
                                .th().text("price").__()
                                .th().text("change").__()
                                .th().text("ratio").__()
                            .__() // tr
                        .__() // thead
                        .tbody()
                        .of(tbody -> IntStream
                            .rangeClosed(1, 20)
                            .forEach(index -> {
                                tbody
                                    .tr().attrClass(index % 2 == 0 ? "even" : "odd")
                                        .td().text(index).__()
                                        .td()
                                            .a().<List<Stock>>dynamic((a, stocks) -> {
                                                Stock stock = stocks.get(index-1);
                                                a.attrHref("/stocks/" + stock.getSymbol()).text(stock.getSymbol());
                                            }
                                            ).__()
                                        .__()
                                        .td()
                                            .a().<List<Stock>>dynamic((a, stocks) -> {
                                                Stock stock = stocks.get(index-1);
                                                a.attrHref(stock.getUrl()).text(stock.getName());
                                            })
                                        .__()
                                        .__()
                                        .td()
                                            .strong().<List<Stock>>dynamic((strong, stocks) -> {
                                                Stock stock = stocks.get(index-1);
                                                strong.text(stock.getPrice());
                                            })
                                            .__()
                                        .__()
                                        .td()
                                            .<List<Stock>>dynamic((td, stocks) -> {
                                                double change = stocks.get(index-1).getChange();
                                                if (change < 0) {
                                                    td.attrClass("minus");
                                                }
                                                td.text(change);
                                            })
                                        .__()
                                        .td()
                                            .<List<Stock>>dynamic((td, stocks) -> {
                                                double ratio = stocks.get(index-1).getRatio();
                                                if (ratio < 0) {
                                                    td.attrClass("minus");
                                                }
                                                td.text(ratio);
                                            })
                                        .__() // td
                                    .__(); // tr
                            })
                        )
                        .__() // tbody
                    .__() // table
                .__() // body
            .__(); // html
    }

    private static final String STOCKS_CSS = "/*<![CDATA[*/\n" +
        "body {\n" +
        "\tcolor: #333333;\n" +
        "\tline-height: 150%;\n" +
        "}\n" +
        "\n" +
        "thead {\n" +
        "\tfont-weight: bold;\n" +
        "\tbackground-color: #CCCCCC;\n" +
        "}\n" +
        "\n" +
        ".odd {\n" +
        "\tbackground-color: #FFCCCC;\n" +
        "}\n" +
        "\n" +
        ".even {\n" +
        "\tbackground-color: #CCCCFF;\n" +
        "}\n" +
        "\n" +
        ".minus {\n" +
        "\tcolor: #FF0000;\n" +
        "}\n" +
        "\n" +
        "/*]]>*/";
}
