package org.vanautrui.octofinsights.controllers.auth;

import j2html.tags.ContainerTag;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.vanautrui.octofinsights.db_utils.DBUtils;
import org.vanautrui.octofinsights.html_util_domain_specific.HeadUtil;
import org.vanautrui.vaquitamvc.controller.VaquitaController;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPEntityEnclosingRequest;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPRequest;
import org.vanautrui.vaquitamvc.responses.VaquitaHTMLResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaRedirectToGETResponse;

import java.net.URLDecoder;
import java.sql.Connection;
import java.util.Map;

import static j2html.TagCreator.*;
import static org.vanautrui.octofinsights.generated.tables.Users.USERS;

public class RegisterController extends VaquitaController {

    public static String regex_alphanumeric = "^[a-zA-Z0-9]+$";

    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPRequest vaquitaHTTPRequest) throws Exception {



        ContainerTag page =
                html(
                        HeadUtil.makeHead(),
                        body(
                                div(
                                        attrs(".container"),
                                        div(
                                                div(
                                                        h1("Octofinsights Register Form").withClasses("text-center"),
                                                        form(
                                                                div(
                                                                        label("Username"),
                                                                        input()
                                                                                .withName("username")
                                                                                .withPlaceholder("username")
                                                                                .withType("text")
                                                                                .attr("pattern",regex_alphanumeric)
                                                                                .attr("required")
                                                                                .withClasses("form-control")

                                                                ).withClasses("form-group"),
                                                                div(
                                                                        label("Email"),
                                                                        input()
                                                                                .withName("email")
                                                                                .withPlaceholder("email")
                                                                                .withType("email")
                                                                                .attr("required")
                                                                                .withClasses("form-control")

                                                                ).withClasses("form-group"),
                                                                div(
                                                                        label("Password"),
                                                                        input()
                                                                                .withName("password")
                                                                                .withPlaceholder("password")
                                                                                .withType("password")
                                                                                .attr("pattern",regex_alphanumeric)
                                                                                .attr("required")
                                                                                .withClasses("form-control")
                                                                ).withClasses("form-group"),
                                                                div(
                                                                        label("Complete the Challenge: what is (2*4) + 1  "),
                                                                        input().withName("challenge").withType("text").withClasses("form-control").attr("required")
                                                                ).withClasses("form-group"),
                                                                button(attrs(".btn .btn-primary .col-md-12"),"Register").withType("submit")
                                                        )
                                                                .withAction("/register")
                                                                .withMethod("post")
                                                                .attr("autocomplete","off")
                                                )
                                        ).withClasses("row justify-content-center")
                                )
                        )
                );

        return new VaquitaHTMLResponse(200,page.render());
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest vaquitaHTTPEntityEnclosingRequest) throws Exception {

        Map<String,String> params= vaquitaHTTPEntityEnclosingRequest.getPostParameters();

        String username = URLDecoder.decode(params.get("username"));
        String email = URLDecoder.decode(params.get("email"));
        String password = URLDecoder.decode(params.get("password"));

        Integer challenge_solution = Integer.parseInt(params.get("challenge"));

        if(challenge_solution!=9){
            throw new Exception("form submission did not solve challenge. could be a bot.");
        }

        if(!username.matches(regex_alphanumeric) || !password.matches(regex_alphanumeric)){
            throw new Exception("username or password do not match requested format");
        }

        //insert new user into the database

        Connection conn = DBUtils.makeDBConnection();

        DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

        Result<Record1<Integer>> fetch = create.select(USERS.ID).from(USERS).where(USERS.USERNAME.eq(username).or(USERS.EMAIL.eq(email))).fetch();

        if(fetch.size()==0) {
            create.insertInto(USERS).columns(USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL).values(username, password, email).execute();
        }else{
            System.out.println("\t user with this username or email already exists");
        }

        conn.close();

        return new VaquitaRedirectToGETResponse("/",vaquitaHTTPEntityEnclosingRequest);
    }
}