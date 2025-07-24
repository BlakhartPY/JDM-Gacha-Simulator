<?php
$host = "localhost";
$user = "root";
$pass = "";
$dbname = "gacha";

// Create connection
$con = mysqli_connect($host, $user, $pass, $dbname);

// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
?>
