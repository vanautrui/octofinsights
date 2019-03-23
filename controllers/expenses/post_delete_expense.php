<?php

include_once("../../base.php");
include_once($absolute_file_url . "/authentication/is_authenticated_otherwise_redirect.php");
include_once($absolute_file_url . "/services/Expense_Entity_service.php");

if(!$_SERVER["REQUEST_METHOD"] === "POST"){
    http_response_code(405);
    exit();
}

//check for post parameters
if( isset($_POST["id"]) ){

    echo("<p>post parameters correct</p>");

    delete_expense_entity_by_id($_POST["id"]);
}

header("Location: " . $baseurl . "/controllers/expenses/get_expenses.php");