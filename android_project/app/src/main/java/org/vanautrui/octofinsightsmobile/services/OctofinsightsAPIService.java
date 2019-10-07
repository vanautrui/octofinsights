package org.vanautrui.octofinsightsmobile.services;

import android.content.Context;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.util.Optional;

public class OctofinsightsAPIService {

    private static Optional<String> username=Optional.empty();
    private static Optional<String> password=Optional.empty();
    private static Optional<Long> access_token=Optional.empty();

    private static synchronized String getAPIEndpointBaseURL(){
        return "https://octofinsights.vanautrui.org/api/";
    }

    public static synchronized void retrieve_and_store_access_token(final String myusername, final String mypassword) throws Exception {
        //TODO: create the appropriate code in the backend so that the android app can retrieve an access token
        //and can use it as a query parameter in further requests to access the api for the user

        username=Optional.of(myusername);
        password=Optional.of(mypassword);

        OctofinsightsAPIService dummy = new OctofinsightsAPIService();

        if(access_token.isPresent()){
            return;
        }else{

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet get_req_access_token = new HttpGet(getAPIEndpointBaseURL()+"request_access_token?username="+username.get()+"&password="+password.get());
            CloseableHttpResponse resp = httpClient.execute(get_req_access_token);

            if(resp.getStatusLine().getStatusCode()==200) {
                access_token = Optional.of(Long.parseLong(EntityUtils.toString(resp.getEntity())));
            }
            throw new Exception("could not get access token ");
        }
    }

    public static synchronized long getBalance()throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get_req_access_token = new HttpGet(getAPIEndpointBaseURL()+"profit");
        CloseableHttpResponse resp = httpClient.execute(get_req_access_token);

        //parse json
        JSONObject jsonObject = new JSONObject(EntityUtils.toString(resp.getEntity()));

        return jsonObject.getLong("value");
    }
}
