<?php

class InventoryItem{
    public $name;

    public $price;

    public $id;

    function __construct($name,$price,$role)
    {
        $this->name=$name;
        $this->price=$price;
    }

    function toString(){
        return $this->name . " " . " [Price: " . $this->price . "]";
    }
}

?>