package org.vanautrui.octofinsights.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.entity.ContentType;
import org.vanautrui.octofinsights.services.ExpensesService;
import org.vanautrui.octofinsights.services.SalesService;
import org.vanautrui.vaquitamvc.VApp;
import org.vanautrui.vaquitamvc.controller.IVGETHandler;
import org.vanautrui.vaquitamvc.requests.VHTTPGetRequest;
import org.vanautrui.vaquitamvc.responses.IVHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VJsonResponse;
import org.vanautrui.vaquitamvc.responses.VTextResponse;
import spark.Request;
import spark.Response;

import java.util.concurrent.atomic.AtomicLong;


public final class ProfitEndpoint {

    public static Object get(Request request, Response response) {
        if(req.session().isPresent() && req.session().get().containsKey("user_id")){
            final int user_id = Integer.parseInt(req.session().get().get("user_id"));

            final AtomicLong x1 = new AtomicLong(0);
            final AtomicLong x2 = new AtomicLong(0);

            //https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        x1.set(SalesService.getTotalForThisMonth(user_id));
                    } catch (Exception e) {
                        //
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        x2.set(ExpensesService.getTotalForThisMonth(user_id));
                    } catch (Exception e) {
                        //
                    }
                }
            });

            //start both db queries
            t1.start();
            t2.start();

            //wait for both to finish
            try {
                t1.join();
                t2.join();
            }catch (Exception e){
                e.printStackTrace();
            }

            final long balance = x1.get()+x2.get();

            final ObjectNode node = (new ObjectMapper()).createObjectNode();
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
