package org.vanautrui.octofinsights.controllers.other.invoices;

import org.vanautrui.octofinsights.html_util_domain_specific.HeadUtil;
import org.vanautrui.octofinsights.html_util_domain_specific.NavigationUtil;
import org.vanautrui.vaquitamvc.VApp;
import org.vanautrui.vaquitamvc.controller.IVGETHandler;
import org.vanautrui.vaquitamvc.requests.VHTTPGetRequest;
import org.vanautrui.vaquitamvc.responses.IVHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VHTMLResponse;
import org.vanautrui.vaquitamvc.responses.VRedirectToGETResponse;

import static j2html.TagCreator.*;

public class InvoicesController implements IVGETHandler {

    //https://codeburst.io/generate-pdf-invoices-with-javascript-c8dbbfb56361

    @Override
    public IVHTTPResponse handleGET(VHTTPGetRequest request, VApp app) throws Exception {
        if( request.session().isPresent() && request.session().get().containsKey("authenticated") && request.session().get().get("authenticated").equals("true")
                && request.session().get().containsKey("user_id")
        ){

            int user_id = Integer.parseInt(request.session().get().get("user_id"));

            //TODO



            //<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js" integrity="sha256-gJWdmuCRBovJMD9D/TVdo4TIK8u5Sti11764sZT1DhI=" crossorigin="anonymous"></script>

            String page=
                    html(
                            HeadUtil.makeHead(
                                    script()
                                            .withSrc("https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js")
                                            .attr("crossorigin","anonymous")
                            ),
                            body(
                                    NavigationUtil.createNavbar(request.session().get().get("username"),"Invoices"),
                                    div(attrs(".container"),
                                            div(attrs("#main-content"),
                                                    p("TODO: make feature to generate invoices"),

                                                    div(
                                                            div(
                                                                    label("Service/Product Provided"),
                                                                    input()
                                                                            .withName("service_or_product")
                                                                            .withType("text")
                                                                            .withClasses("form-control")
                                                                            .withId("product_or_service")
                                                            ).withClasses("form-group col-md-8"),
                                                            div(
                                                                    label("Price"),
                                                                    input()
                                                                            .withName("price")
                                                                            .withType("number")
                                                                            .withClasses("form-control")
                                                                            .withId("price")
                                                            ).withClasses("form-group").withClasses("col-md-2"),
                                                            div(
                                                                    button(attrs(".btn .btn-primary .m-2 .p-3"),"Enter ")
                                                                            .withId("enterButton")
                                                                            .withType("submit")
                                                                            .attr("onclick","entervalue")
                                                            ).withClasses("col-md-2")
                                                    ).withClasses("row"),

                                                    ul(

                                                    ).withId("invoice-list")
                                                            .withClasses("list-group"),

                                                    hr(),
                                                    button("Generate PDF")
                                                            .withId("generateButton")
                                                            .withClasses("btn btn-primary")
                                            )
                                    ),
                                    script().withSrc("invoices.js")
                            )
                    ).render();

            return new VHTMLResponse(200,page);

        }else {
            return new VRedirectToGETResponse("/login", request);
        }
    }
}