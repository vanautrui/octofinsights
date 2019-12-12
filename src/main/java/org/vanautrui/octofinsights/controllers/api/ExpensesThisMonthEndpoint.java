package org.vanautrui.octofinsights.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.entity.ContentType;
import org.vanautrui.octofinsights.services.ExpensesService;
import org.vanautrui.vaquitamvc.VApp;
import org.vanautrui.vaquitamvc.controller.IVGETHandler;
import org.vanautrui.vaquitamvc.requests.VHTTPGetRequest;
import org.vanautrui.vaquitamvc.responses.IVHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VJsonResponse;
import org.vanautrui.vaquitamvc.responses.VTextResponse;
import spark.Request;
import spark.Response;


public final class ExpensesThisMonthEndpoint {

    public static Object get(Request request, Response response) {
        if(req.session().isPresent() && req.session().get().containsKey("user_id")){
            int user_id = Integer.parseInt(req.session().get().get("user_id"));


            long balance = 0;
            try {
                balance = ExpensesService.getTotalForThisMonth(user_id);
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                response.type(ContentType.TEXT_PLAIN.toString());
                return e.getMessage();
            }

            ObjectNode node = (new ObjectMapper()).createObjectNode();

            node.put("value",balance);

            response.status(200);
            response.type(ContentType.APPLICATION_JSON.toString());
            return (node.toPrettyString());
        }else{

            response.status(400);
            response.type(ContentType.TEXT_PLAIN.toString());
            return ("Bad Request, no user_id found in session.");
        }
    }
}
