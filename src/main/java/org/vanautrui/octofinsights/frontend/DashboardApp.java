package org.vanautrui.octofinsights.frontend;

import def.dom.HTMLElement;
import def.dom.ProgressEvent;
import def.dom.XMLHttpRequest;
import def.jquery.JQuery;
import def.js.JSON;

import java.util.function.Function;

import static def.dom.Globals.console;
import static def.jquery.Globals.$;

public class DashboardApp {
    public static void main(String[] args) {
        console.log("Dashboard Frontend App Running");

        //HTMLElement test = $("#test").get()[0];

        XMLHttpRequest Http = new XMLHttpRequest();

        Http.open("GET","/api/cashflow",true);
        Http.send();

        Http.onloadend=new Function<ProgressEvent, Object>() {
            @Override
            public Object apply(ProgressEvent progressEvent) {
                StringIntPair[] parse = (StringIntPair[]) JSON.parse(Http.responseText);

                //console.log(parse[0]);

                //console.log(parse);
                return null;
            }
        };

        //test.textContent="hi frontend apps in java !";

        main2();
        main3();
        main4();
        main5();
    }

    public static void main2(){
        JQuery balancediv = $("#balancediv");
        //balancediv.hide();
        lowOpacity(balancediv);

        XMLHttpRequest Http = new XMLHttpRequest();

        Http.open("GET","/api/value",true);


        Http.onloadend=new Function<ProgressEvent, Object>() {
            @Override
            public Object apply(ProgressEvent progressEvent) {
                IntObject parse = (IntObject) JSON.parse(Http.responseText);

                //console.log(parse.value);

                HTMLElement balance = $("#balance").get()[0];
                balance.textContent=parse.value +" €";
                if(parse.value>=0) {
                    balance.classList.add("text-success");
                }else {
                    balance.classList.add("text-danger");
                }
                //balancediv.fadeIn();
                fullOpacity(balancediv);
                return null;
            }
        };
        Http.send();
    }

    public static void main3(){
        HTMLElement sales = $("#salesthismonth").get()[0];
        JQuery sales_div = $("#salesdiv");
        lowOpacity(sales_div);
        //sales_div.hide();

        XMLHttpRequest Http = new XMLHttpRequest();

        Http.open("GET","/api/salesthismonth",true);


        Http.onloadend=new Function<ProgressEvent, Object>() {
            @Override
            public Object apply(ProgressEvent progressEvent) {
                IntObject parse = (IntObject) JSON.parse(Http.responseText);

                //console.log(parse.value);


                sales.textContent=parse.value+" €";

                if(parse.value>0){
                    sales.classList.add("text-success");
                }

                //sales_div.slideDown();
                //sales_div.fadeIn();
                fullOpacity(sales_div);

                return null;
            }
        };

        Http.send();
    }


    public static void main4(){
        JQuery profitdiv = $("#profitdiv");
        //profitdiv.hide();
        //profitdiv.get()[0].style.opacity="0.1";
        lowOpacity(profitdiv);
        XMLHttpRequest Http = new XMLHttpRequest();
        Http.open("GET","/api/profit",true);


        Http.onloadend=new Function<ProgressEvent, Object>() {
            @Override
            public Object apply(ProgressEvent progressEvent) {
                IntObject parse = (IntObject) JSON.parse(Http.responseText);

                $("#profit").get()[0].textContent=parse.value+" €";
                //profitdiv.fadeIn();
                //profitdiv.get()[0].style.opacity="1.0";
                fullOpacity(profitdiv);
                return null;
            }
        };

        Http.send();
    }

    public static void main5(){
        JQuery expensesdiv = $("#expensesdiv");
        lowOpacity(expensesdiv);
        //expensesdiv.get()[0].style.opacity="0.1";
        XMLHttpRequest Http = new XMLHttpRequest();

        Http.open("GET","/api/expensesthismonth",true);


        Http.onloadend=new Function<ProgressEvent, Object>() {
            @Override
            public Object apply(ProgressEvent progressEvent) {
                IntObject parse = (IntObject) JSON.parse(Http.responseText);

                $("#expensesthismonth").get()[0].textContent=((-1)*parse.value)+" €";
                //expensesdiv.get()[0].style.opacity="1.0";
                fullOpacity(expensesdiv);
                return null;
            }
        };

        Http.send();
    }

    private static void lowOpacity(JQuery element){
        element.get()[0].style.opacity="0.1";
    }

    private static void fullOpacity(JQuery element){
        element.get()[0].style.opacity="1.0";
    }
}
