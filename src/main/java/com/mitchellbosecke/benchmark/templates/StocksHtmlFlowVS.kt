package com.mitchellbosecke.benchmark.templates

import com.mitchellbosecke.benchmark.model.Stock
import com.mitchellbosecke.benchmark.templates.StocksHtmlFlow.STOCKS_CSS
import htmlflow.HtmlFlow
import htmlflow.HtmlView
import htmlflow.forEachIndexedOf
import htmlflow.html
import org.xmlet.htmlapifaster.EnumHttpEquivType
import org.xmlet.htmlapifaster.EnumMediaType
import org.xmlet.htmlapifaster.EnumRelType
import org.xmlet.htmlapifaster.EnumTypeContentType
import org.xmlet.htmlapifaster.EnumTypeScriptType
import org.xmlet.htmlapifaster.Indexed
import org.xmlet.htmlapifaster.Slot
import org.xmlet.htmlapifaster.a
import org.xmlet.htmlapifaster.body
import org.xmlet.htmlapifaster.h1
import org.xmlet.htmlapifaster.head
import org.xmlet.htmlapifaster.link
import org.xmlet.htmlapifaster.meta
import org.xmlet.htmlapifaster.script
import org.xmlet.htmlapifaster.strong
import org.xmlet.htmlapifaster.style
import org.xmlet.htmlapifaster.table
import org.xmlet.htmlapifaster.tbody
import org.xmlet.htmlapifaster.td
import org.xmlet.htmlapifaster.th
import org.xmlet.htmlapifaster.thead
import org.xmlet.htmlapifaster.title
import org.xmlet.htmlapifaster.tr

object StocksHtmlFlowVS {
    val view: HtmlView<List<Stock>> =
        HtmlFlow.view<List<Stock>> { page ->
            page.html {
                head {
                    title { text("Stock Prices") }
                    meta {
                        attrHttpEquiv(EnumHttpEquivType.CONTENT_TYPE)
                        attrContent("text/html; charset=UTF-8")
                    }
                    meta {
                        addAttr("http-equiv", "Content-Style-Type")
                        attrContent("text/CSS")
                    }
                    meta {
                        addAttr("http-equiv", "Content-Script-Type")
                        attrContent("text/javascript")
                    }
                    link {
                        addAttr("rel", "shortcut icon")
                        attrHref("/images/favicon.ico")
                    }
                    link {
                        attrRel(EnumRelType.STYLESHEET)
                        attrType(EnumTypeContentType.TEXT_CSS)
                        attrHref("/CSS/style.css")
                        attrMedia(EnumMediaType.ALL)
                    }
                    script {
                        attrType(EnumTypeScriptType.TEXT_JAVASCRIPT)
                        attrSrc("/js/util.js")
                    }
                    style {
                        attrType(EnumTypeContentType.TEXT_CSS)
                        raw(STOCKS_CSS)
                    }
                }
                body {
                    h1 { text("Stock Prices") }
                    table {
                        thead {
                            tr {
                                th { text("#") }
                                th { text("Symbol") }
                                th { text("Name") }
                                th { text("Price") }
                                th { text("Change") }
                                th { text("Ratio") }
                            }
                        }
                        tbody {
                            forEachIndexedOf { row: Slot<Indexed<Stock>> ->
                                tr {
                                    attrOf("class", row.read { if (it.index() % 2 == 0) "odd" else "even" })
                                    td {
                                        intOf(row.readInt { it.index() + 1 })
                                    }
                                    td {
                                        a {
                                            attrOf("href", row.read { "/stocks/" + it.item().symbol })
                                            rawOf(row.read { it.item().symbol })
                                        }
                                    }
                                    td {
                                        a {
                                            attrOf("href", row.read { it.item().url })
                                            rawOf(row.read { it.item().name })
                                        }
                                    }
                                    td {
                                        strong {
                                            doubleOf(row.readDouble { it.item().price })
                                        }
                                    }
                                    td {
                                        attrOfNullable(
                                            "class",
                                            row.read { if (it.item().change < 0) "minus" else null },
                                        )
                                        doubleOf(row.readDouble { it.item().change })
                                    }
                                    td {
                                        attrOfNullable(
                                            "class",
                                            row.read { if (it.item().ratio < 0) "minus" else null },
                                        )
                                        doubleOf(row.readDouble { it.item().ratio })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}
