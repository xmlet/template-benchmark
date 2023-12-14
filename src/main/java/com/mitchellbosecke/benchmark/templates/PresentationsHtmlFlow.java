package com.mitchellbosecke.benchmark.templates;

import com.mitchellbosecke.benchmark.model.Presentation;
import htmlflow.HtmlFlow;
import htmlflow.HtmlPage;
import htmlflow.HtmlView;
import org.xmlet.htmlapifaster.EnumHttpEquivType;
import org.xmlet.htmlapifaster.EnumMediaType;
import org.xmlet.htmlapifaster.EnumRelType;

import java.util.Iterator;
import java.util.stream.IntStream;

public class PresentationsHtmlFlow {

    public static HtmlView view = HtmlFlow.view(PresentationsHtmlFlow::presentationsView);

    private static void presentationsView(HtmlPage view){
        view.html()
            .head()
                .meta().attrCharset("utf-8").__()
                .meta().attrName("viewport").attrContent("width=device-width, initial-scale=1.0").__()
                .meta().attrHttpEquiv(EnumHttpEquivType.CONTENT_LANGUAGE).attrContent("IE=Edge").__()
                .title().text("JFall 2013 Presentations - htmlApi").__()
                .link().attrRel(EnumRelType.STYLESHEET).attrHref("/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css").attrMedia(EnumMediaType.SCREEN).__()
            .__()
            .body()
                .div().attrClass("container")
                    .div().attrClass("page-header")
                        .h1().text("JFall 2013 Presentations - htmlApi").__()
                    .__() // div
                    .of(containerDiv -> IntStream.range(0, 10).forEach(
                            ignore ->
                                containerDiv
                                    .div().attrClass("panel panel-default")
                                            .<Iterator<Presentation>>dynamic((div, iter) -> {
                                                Presentation presentation = iter.next();
                                                div.div().attrClass("panel-heading")
                                                    .h3().attrClass("panel-title")
                                                        .text(presentation.getTitle() + " - " + presentation.getSpeakerName())
                                                    .__()
                                                .__()
                                                .div()
                                                    .attrClass("panel-body").text(presentation.getSummary())
                                                .__();
                                            })
                                    .__())
                    )
                .__()
                .script().attrSrc("/webjars/jquery/3.1.1/jquery.min.js").__()
                .script().attrSrc("/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js").__()
            .__()
        .__();
    }
}
