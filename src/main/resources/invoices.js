const pdf=new jsPDF();

let button = document.getElementById('generateButton');

button.addEventListener('click',printPDF);

let mybutton = document.getElementById('enterButton');

mybutton.addEventListener('click',entervalue);

var list_items=[];

function printPDF(){
    //alert("print pdf");

    //left offset, top offset, text
    pdf.text(10,10,"Yay PDF");

    pdf.save();
}

function update(){
    //clear the list
    var list = document.getElementById("invoice-list");
    list.innerHTML="";

    //add all the items
    list_items.forEach(function(item){

        update_one(item.product_or_service,item.price,item.id);
    });
}

function update_one(product_or_service,price,id){
    var list = document.getElementById("invoice-list");

    var item_price=document.createElement("p");
    item_price.innerHTML=price+"";
    item_price.className="col-md-3";

    var item_name=document.createElement("p");
    item_name.innerHTML=product_or_service;
    item_name.className="col-md-8";

    var item_delete_button = document.createElement("button");
    item_delete_button.className="btn btn-outline-danger col-md-1";
    item_delete_button.onclick=delete_item;
    item_delete_button.innerHTML="X";
    item_delete_button.id=id+"";

    var item = document.createElement("li");
    item.className="list-group-item";

    var div = document.createElement("div");
    div.className="row";

    list.appendChild(item);

    div.appendChild(item_name);
    div.appendChild(item_price);
    div.appendChild(item_delete_button);

    item.appendChild(div);
}

function make_item(product_or_service,price){
    return {
        product_or_service: product_or_service,
        price:price,
        id:Math.floor((Math.random()*1000000))+""
    };
}

function delete_item(e){
    console.log("try to delete an item");
    var delete_id = e.target.id;
    list_items=list_items.filter(function(item){
        return (item.id != delete_id)
    });

    update();
}

function entervalue(e){
    //alert("enter value");
    console.log(e);

    var product_or_service = document.getElementById("product_or_service").value;
    var price = document.getElementById("price").value;

    document.getElementById("product_or_service").value="";
    document.getElementById("price").value="";

    if(product_or_service=="" || price == ""){
        //do not operate on empty values
        return;
    }

    list_items.push(make_item(product_or_service,price));

    update();
}