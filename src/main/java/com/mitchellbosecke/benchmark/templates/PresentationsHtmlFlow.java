package com.mitchellbosecke.benchmark.templates;

import com.mitchellbosecke.benchmark.model.Presentation;
import htmlflow.DynamicHtml;
import org.xmlet.htmlapifaster.EnumHttpEquivType;
import org.xmlet.htmlapifaster.EnumMediaType;
import org.xmlet.htmlapifaster.EnumRelType;

public class PresentationsHtmlFlow {

    public static DynamicHtml<Iterable<Presentation>> view = DynamicHtml.view(PresentationsHtmlFlow::presentationsView);

    private static void presentationsView(DynamicHtml<Iterable<Presentation>> view, Iterable<Presentation> presentations){
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
                    .of(containerDiv ->
                        presentations.forEach(
                            presentation ->
                                containerDiv
                                    .div().attrClass("panel panel-default")
                                        .div().attrClass("panel-heading")
                                            .h3().attrClass("panel-title")
                                                .dynamic(h3 -> h3.text(presentation.getTitle() + " - " + presentation.getSpeakerName()))
                                            .__()
                                        .__()
                                        .div().attrClass("panel-body")
                                            .dynamic(div -> div.text(presentation.getSummary()))
                                        .__()
                                    .__())
                    )
                .__()
                .script().attrSrc("/webjars/jquery/3.1.1/jquery.min.js").__()
                .script().attrSrc("/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js").__()
            .__()
        .__();
    }
}
