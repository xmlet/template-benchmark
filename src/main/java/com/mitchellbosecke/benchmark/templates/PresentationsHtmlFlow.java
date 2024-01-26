package com.mitchellbosecke.benchmark.templates;

import com.mitchellbosecke.benchmark.model.Presentation;
import htmlflow.HtmlFlow;
import htmlflow.HtmlPage;
import htmlflow.HtmlView;
import org.xmlet.htmlapifaster.EnumHttpEquivType;
import org.xmlet.htmlapifaster.EnumMediaType;
import org.xmlet.htmlapifaster.EnumRelType;

import java.util.Iterator;

public class PresentationsHtmlFlow {

    public static HtmlView<Iterator<Presentation>> view = HtmlFlow.view(PresentationsHtmlFlow::presentationsView);

    private static void presentationsView(HtmlPage view){
        view.html()
            .head()
                .meta().attrCharset("utf-8").__()
                .meta().attrName("viewport").attrContent("width=device-width, initial-scale=1.0").__()
                .meta().attrHttpEquiv(EnumHttpEquivType.CONTENT_LANGUAGE).attrContent("IE=Edge").__()
                .title().raw("JFall 2013 Presentations - htmlApi").__()
                .link().attrRel(EnumRelType.STYLESHEET).attrHref("/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css").attrMedia(EnumMediaType.SCREEN).__()
            .__()
            .body()
                .div().attrClass("container")
                    .div().attrClass("page-header")
                        .h1().raw("JFall 2013 Presentations - htmlApi").__()
                    .__() // div
                    .<Iterator<Presentation>>dynamic((div, iter) -> {
                        while(iter.hasNext()) {
                            div.raw(presentationPartial.render(iter.next()));
                        }
                    })
                .__()
                .script().attrSrc("/webjars/jquery/3.1.1/jquery.min.js").__()
                .script().attrSrc("/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js").__()
            .__()
        .__();
    }

    private static HtmlView<Presentation> presentationPartial = HtmlFlow.view(page -> page
        .div().attrClass("panel panel-default")
            .div().attrClass("panel-heading")
                .h3().attrClass("panel-title")
                .<Presentation>dynamic((h3, presentation) ->
                    h3.raw(presentation.getTitle() + " - " + presentation.getSpeakerName()))
                .__() // h3
            .__() // div
            .div()
                .attrClass("panel-body")
                .<Presentation>dynamic((div, presentation) ->
                    div.raw(presentation.getSummary()))
            .__() // div
        .__() // div
    );
}
