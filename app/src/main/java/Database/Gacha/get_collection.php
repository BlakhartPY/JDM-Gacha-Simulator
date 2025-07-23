<?php
include_once("connects.php");

$user_id = $_POST['user_id'];

$result = mysqli_query($con, "SELECT card_name, count FROM collections WHERE user_id = '$user_id'");
$cards = [];

while ($row = mysqli_fetch_assoc($result)) {
    $cards[] = $row;
}

header('Content-Type: application/json');
echo json_encode($cards);
?>
