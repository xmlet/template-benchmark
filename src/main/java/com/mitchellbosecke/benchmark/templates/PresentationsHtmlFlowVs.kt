package com.mitchellbosecke.benchmark.templates

import com.mitchellbosecke.benchmark.model.Presentation
import htmlflow.HtmlFlow
import htmlflow.HtmlView
import htmlflow.forEachOf
import htmlflow.html
import org.xmlet.htmlapifaster.EnumHttpEquivType
import org.xmlet.htmlapifaster.EnumMediaType
import org.xmlet.htmlapifaster.EnumRelType
import org.xmlet.htmlapifaster.Slot
import org.xmlet.htmlapifaster.body
import org.xmlet.htmlapifaster.div
import org.xmlet.htmlapifaster.h1
import org.xmlet.htmlapifaster.h3
import org.xmlet.htmlapifaster.head
import org.xmlet.htmlapifaster.link
import org.xmlet.htmlapifaster.meta
import org.xmlet.htmlapifaster.script
import org.xmlet.htmlapifaster.title

object PresentationsHtmlFlowVs {
    val view: HtmlView<Iterable<Presentation>> =
        HtmlFlow.view<Iterable<Presentation>> { page ->
            page.html {
                head {
                    meta {
                        attrCharset("utf-8")
                    }
                    meta {
                        attrName("viewport")
                        attrContent("width=device-width, initial-scale=1.0")
                    }
                    meta {
                        attrHttpEquiv(EnumHttpEquivType.CONTENT_LANGUAGE)
                        attrContent("IE=Edge")
                    }
                    title {
                        text("JFall 2013 Presentations - htmlApi")
                    }
                    link {
                        attrRel(EnumRelType.STYLESHEET)
                        attrHref("/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css")
                        attrMedia(EnumMediaType.SCREEN)
                    }
                }
                body {
                    div {
                        attrClass("container")
                        div {
                            attrClass("page-header")

                            h1 {
                                text("JFall 2013 Presentations - htmlApi")
                            }
                        }
                        forEachOf { presentation: Slot<Presentation> ->
                            div {
                                attrClass("panel panel-default")
                                div {
                                    attrClass("panel-heading")
                                    h3 {
                                        attrClass("panel-title")
                                        rawOf(
                                            presentation.read {
                                                it.title + " - " + it.speakerName
                                            },
                                        )
                                    }
                                }
                                div {
                                    attrClass("panel-body")
                                    rawOf(
                                        presentation.read { it.summary },
                                    )
                                }
                            }
                        }
                    }
                    script { attrSrc("/webjars/jquery/3.1.1/jquery.min.js") }
                    script { attrSrc("/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js") }
                }
            }
        }
}
