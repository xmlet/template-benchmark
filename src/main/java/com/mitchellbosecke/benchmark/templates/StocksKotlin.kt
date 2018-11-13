package com.mitchellbosecke.benchmark.templates

import com.mitchellbosecke.benchmark.model.Stock
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize

class StocksKotlin {
    companion object {

        fun stocksTemplate(stocks : List<Stock> ): String {
            return createHTMLDocument()
                .html {
                    head {
                        title { text("Stock Prices") }
                        meta {httpEquiv = MetaHttpEquiv.contentType; content = "text/html; charset=UTF-8" }
                        meta {httpEquiv="Content-Style-Type"; content="text/css" }
                        meta {httpEquiv="Content-Script-Type"; content="text/javascript"}
                        link {rel="shortcut icon"; href="/images/favicon.ico"}
                        link {rel= LinkRel.stylesheet; type= LinkType.textCss; href="/css/style.css"; media= LinkMedia.all}
                        script {type= ScriptType.textJavaScript; src="/js/util.js" }
                        style {type= StyleType.textCss;text("[body {  color: #333333;  line-height: 150%;}thead {  font-weight: bold;  background-color: #CCCCCC;}.odd {  background-color: #FFCCCC;}.even {  background-color: #CCCCFF;}.minus {  color: #FF0000;}]]>")}
                    }
                    body {
                        h1 { text("Stock Prices") }
                        table {
                            thead {
                                tr {
                                    th { text("#") }
                                    th { text("symbol") }
                                    th { text("name") }
                                    th { text("price") }
                                    th { text("change") }
                                    th { text("ratio") }
                                }
                            }
                            tbody {
                                var index = 0
                                stocks.forEach {
                                    tr {
                                        classes = setOf( if (index % 2 == 0) "even" else "odd")
                                        td { index++ }
                                        td { a { href = it.url ; text(it.name) } }
                                        td { strong { text(it.price) } }
                                        td {
                                            if (it.change < 0) classes = setOf("minus")
                                            text(it.change)
                                        }
                                        td {
                                            if (it.ratio < 0) classes = setOf("minus")
                                            text(it.ratio)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }.serialize(false)
        }
    }
}

