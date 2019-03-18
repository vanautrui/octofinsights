<div id="sidebar" class="col-md-2 sidebar">

    <h2 class="text-center">
        OctoFinsights
    </h2>
    <?php

        include("../base.php");

        $links = array(
                new MenuItem("Dashboard",$baseurl . "/index.php"),
            new MenuItem("Reports(TODO)","."),
            new MenuItem("Sales",$baseurl . "/controllers/sales" . "/get_sales.php"),
            new MenuItem("Leads",$baseurl . "/leads.php"),
            new MenuItem("Employees",$baseurl . "/employees.php"),
            new MenuItem("Inventory",$baseurl . "/inventory.php")
        );

        class MenuItem{

            public $name;
            public $link;

            function __construct($name,$link)
            {
                $this->name=$name;
                $this->link=$link;
            }
        }
    ?>

    <ul class="list-group">
        <?php
            for($i=0;$i<sizeof($links);$i++){
                echo "<a href='" . $links[$i]->link . "'>";
                    echo "<li class='list-group-item'>";
                        echo $links[$i]->name;
                    echo "</li>";
                echo "</a>";
            }
        ?>
    </ul>

</div>