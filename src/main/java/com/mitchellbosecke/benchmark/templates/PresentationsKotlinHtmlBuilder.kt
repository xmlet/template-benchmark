package com.mitchellbosecke.benchmark.templates

import com.mitchellbosecke.benchmark.model.Presentation
import nu.staldal.kotlin.html.*

class PresentationsKotlinHtmlBuilder {
    companion object {

        fun presentationsTemplate(presentations : Collection<Presentation> ): String {
            return htmlDoc(prettyPrint = false) {
                html {
                    head {
                        meta("charset" to "utf-8")
                        meta("name" to "viewport", "content" to "width=device-width, initial-scale=1.0")
                        meta("httpEquiv" to "contentLanguage", "content" to "IE=Edge")
                        title {
                            text("JFall 2013 Presentations - htmlApi")
                        }
                        link(
                            "rel" to "stylesheet",
                            "href" to "/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css",
                            "media" to "screen"
                        )
                    }
                    body {
                        div("class" to "container") {
                            div("class" to "page-header") {
                                h1 { text("JFall 2013 Presentations - htmlApi") }
                            }

                            presentations.forEach {
                                div("class" to "panel panel-default") {
                                    div("class" to "panel-heading") {
                                        h3("class" to "panel-title") {
                                            text(it.title + " - " + it.speakerName)
                                        }
                                    }
                                    div("class" to "panel-body") {
                                        text(it.summary)
                                    }
                                }
                            }
                        }

                        script("src" to "/webjars/jquery/3.1.1/jquery.min.js")
                        script("src" to "/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js")
                    }
                }
            }
        }
    }
}
