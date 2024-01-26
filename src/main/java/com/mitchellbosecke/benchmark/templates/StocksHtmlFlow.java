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

import java.util.List;

public class StocksHtmlFlow {

    public static HtmlView<List<Stock>> view = HtmlFlow.view(StocksHtmlFlow::templateStocks);

    private static void templateStocks(HtmlPage view) {
        view
            .html()
                .head()
                    .title().raw("Stock Prices").__()
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
                        .raw(STOCKS_CSS)
                    .__()
                .__() // head
                .body()
                    .h1().raw("Stock Prices").__()
                    .table()
                        .thead()
                            .tr()
                                .th().raw("#").__()
                                .th().raw("symbol").__()
                                .th().raw("name").__()
                                .th().raw("price").__()
                                .th().raw("change").__()
                                .th().raw("ratio").__()
                            .__() // tr
                        .__() // thead
                        .tbody()
                        .<List<Stock>>dynamic((tbody, stocks) -> {
                            for (int i = 0; i < stocks.size(); i++) {
                                StockDto dto = new StockDto(stocks.get(i), i + 1);
                                tbody.raw(stockPartial.render(dto));
                            }
                        })
                        .__() // tbody
                    .__() // table
                .__() // body
            .__(); // html
    }

    record StockDto(Stock stock, int index) {
    }

    private static HtmlView<StockDto> stockPartial = HtmlFlow.view(page -> page
        .tr().<StockDto>dynamic((tr, dto) -> tr.attrClass(dto.index % 2 == 0 ? "even" : "odd"))
            .td()
                .<StockDto>dynamic((td, dto) -> td.raw(dto.index))
            .__() // td
            .td()
                .a()
                    .<StockDto>dynamic((a, dto) -> a.attrHref("/stocks/" + dto.stock.getSymbol()).raw(dto.stock.getSymbol()))
                .__() // a
            .__() // td
            .td()
                .a().<StockDto>dynamic((a, dto) -> a.attrHref(dto.stock.getUrl()).raw(dto.stock.getName())).__()
            .__() // td
            .td()
                .strong().<StockDto>dynamic((strong, dto) -> strong.raw(dto.stock.getPrice())).__()
            .__() // td
            .td()
                .<StockDto>dynamic((td, dto) ->{
                    double change = dto.stock.getChange();
                    if (change < 0) { td.attrClass("minus"); }
                    td.raw(change);
                })
            .__() // td
            .td()
                .<StockDto>dynamic((td, dto) -> {
                    double ratio = dto.stock.getRatio();
                    if (ratio < 0) { td.attrClass("minus"); }
                    td.raw(ratio);
                })
            .__() // td
        .__() // tr
    );

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
