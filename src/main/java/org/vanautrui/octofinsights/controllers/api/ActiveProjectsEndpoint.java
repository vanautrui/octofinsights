package org.vanautrui.octofinsights.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.vanautrui.octofinsights.services.ProjectsService;
import org.vanautrui.vaquitamvc.VApp;
import org.vanautrui.vaquitamvc.controller.IVGETHandler;
import org.vanautrui.vaquitamvc.requests.VHTTPGetRequest;
import org.vanautrui.vaquitamvc.responses.IVHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VJsonResponse;
import org.vanautrui.vaquitamvc.responses.VTextResponse;


public class ActiveProjectsEndpoint implements IVGETHandler {

    @Override
    public IVHTTPResponse handleGET(VHTTPGetRequest req, VApp app) throws Exception {
        if(
                req.session().isPresent()
                && req.session().get().containsKey("user_id")
        ){
            final int user_id = Integer.parseInt(req.session().get().get("user_id"));

            final long count = ProjectsService.getActiveProjectsCount(user_id);

            final ObjectNode node = (new ObjectMapper()).createObjectNode();

            node.put("value",count);

            return new VJsonResponse(200,node);
        }else{
            return new VTextResponse(400, "Bad Request, no user_id found in session.");
        }
    }
}
