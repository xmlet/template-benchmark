package com.mitchellbosecke.benchmark.templates

import com.mitchellbosecke.benchmark.model.Stock
import nu.staldal.kotlin.html.*

class StocksKotlinHtmlBuilder {
    companion object {

        fun stocksTemplate(stocks : List<Stock> ): String {
            return htmlDoc(prettyPrint = false) {
                html {
                    head {
                        title { text("Stock Prices") }
                        meta("httpEquiv" to "contentType", "content" to "text/html; charset=UTF-8")
                        meta("httpEquiv" to "Content-Style-Type", "content" to "text/css")
                        meta("httpEquiv" to "Content-Script-Type", "content" to "text/javascript")
                        link("rel" to "shortcut icon", "href" to "/images/favicon.ico")
                        link("rel" to "stylesheet", "type" to "text/css", "href" to "/css/style.css", "media" to "all")
                        script("type" to "text/javascript", "src" to "/js/util.js")
                        style("type" to "text/css") {
                            unsafe("[body {  color: #333333;  line-height: 150%;}thead {  font-weight: bold;  background-color: #CCCCCC;}.odd {  background-color: #FFCCCC;}.even {  background-color: #CCCCFF;}.minus {  color: #FF0000;}]]>")
                        }
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
                                    tr("class" to if (index % 2 == 0) "even" else "odd") {
                                        td { index++ }
                                        td { a("href" to it.url) { text(it.name) } }
                                        td { strong { text(it.price.toString()) } }
                                        td(*if (it.change < 0) arrayOf("class" to "minus") else arrayOf()) {
                                            text(it.change.toString())
                                        }
                                        td(*if (it.ratio < 0) arrayOf("class" to "minus") else arrayOf()) {
                                            text(it.ratio.toString())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
