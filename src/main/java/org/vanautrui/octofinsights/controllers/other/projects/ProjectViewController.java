package org.vanautrui.octofinsights.controllers.other.projects;

import j2html.tags.ContainerTag;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.vanautrui.octofinsights.db_utils.DBUtils;
import org.vanautrui.octofinsights.html_util_domain_specific.HeadUtil;
import org.vanautrui.octofinsights.html_util_domain_specific.NavigationUtil;
import org.vanautrui.vaquitamvc.VaquitaApp;
import org.vanautrui.vaquitamvc.controller.VaquitaController;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPEntityEnclosingRequest;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPJustRequest;
import org.vanautrui.vaquitamvc.responses.VaquitaHTMLResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaRedirectResponse;

import java.sql.Connection;

import static j2html.TagCreator.*;
import static java.lang.Integer.parseInt;
import static org.vanautrui.octofinsights.generated.tables.Projects.PROJECTS;

public class ProjectViewController extends VaquitaController {

    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPJustRequest request, VaquitaApp app) throws Exception {

      boolean loggedin=request.session().isPresent() && request.session().get().containsKey("authenticated") && request.session().get().get("authenticated").equals("true");
      if(!loggedin){
          return new VaquitaRedirectResponse("/login",request,app);
      }

      int user_id = parseInt(request.session().get().get("user_id"));

      int project_id=Integer.parseInt(request.getQueryParameter("id"));

      Connection conn= DBUtils.makeDBConnection();

      DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
      Record project = ctx.select(PROJECTS.asterisk()).from(PROJECTS).where(PROJECTS.ID.eq(project_id).and(PROJECTS.USER_ID.eq(user_id))).fetchOne();

      String page =
        html(
          HeadUtil.makeHead(),
          body(
            NavigationUtil.createNavbar(request.session().get().get("username"),"Projects"),
            div(
              h3("Project: "+project.get(PROJECTS.PROJECT_NAME)),
              hr(),
              div(
                      "TODO: project metadata"
              ),
              form(
                div(
                  div(
                    label("Task"),
                    input().withType("text").withClasses("form-control")
                  ).withClasses("form-group col-md-6"),
                  div(
                    label("Time Estimate (hours)"),
                    input().withType("number").withClasses("form-control")
                  ).withClasses("form-group col-md-6")
                ).withClasses("form-row"),
                button("ADD TASK").withClasses("btn","btn-primary","col-md-12").withType("submit")
              ),
              h3("Tasks"),
              ul(
                li(
                        "project example"
                ).withClasses("list-group-item")
              ).withClasses("list-group"),
              h3("Completed Tasks"),
              ul(
                li(
                        "project example"
                ).withClasses("list-group-item")
              ).withClasses("list-group")
            ).withClasses("container")
          )
        ).render();


      return new VaquitaHTMLResponse(200,page);
    }

    private ContainerTag makeCompletedTaske(String task_name,String task_id){
        ContainerTag res=
                li(
                  div(
                    div(
                            s(task_name)
                    ).withClasses("col-md-6"),
                    div(
                      button(
                              "UNARCHIVE"
                      ).withClasses("btn","btn-outline-secondary","m-2"),

                      button(
                              "DELETE"
                      ).withClasses("btn","btn-outline-danger","m-2")


                    ).withClasses("col-md-6","row","justify-content-end")
                  ).withClasses("row")
                ).withClasses("list-group-item");
        return res;
    }

    private ContainerTag makeTask(String task,int task_id){
        ContainerTag res=
                li(
                    div(
                        div(

                              task

                        ).withClasses("col-md-6"),

                        div(

                            button(
                                "COMPLETE"
                            ).withClasses("btn","btn-outline-primary","m-2")


                        ).withClasses("col-md-6","row","justify-content-end")
                    ).withClasses("row")
                ).withClasses("list-group-item");
        return res;
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest request,VaquitaApp app) throws Exception {
      //not implemented
      return null;
    }
}
