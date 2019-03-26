<?php

include_once($_SERVER["DOCUMENT_ROOT"] . "/octofinsights" . "/base.php");
include_once($absolute_file_url . "/include_many.php");

function get_all_employees()
{

    $results = fetch_all_from("employees");

    $typed_results = array();

    for($i=0;$i<sizeof($results);$i++) {
        $employee = $results[$i];
        array_push($typed_results,new Employee_Entity($employee[0],$employee[1],$employee[2],$employee[3]) );
    }

    return $typed_results;
}

function delete_by_id($id){
    delete_from_where_id_is("employees",$id);
}