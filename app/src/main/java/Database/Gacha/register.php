<?php

include_once('connects.php');

$username = $_GET['username'];
$password = $_GET['password'];

// Optional: check if username or password is empty
if (empty($username) || empty($password)) {
    echo "Username and password are required.";
    exit();
}

// Optional: check if username already exists
$check = mysqli_query($con, "SELECT * FROM accounts WHERE username = '$username'");
if (mysqli_num_rows($check) > 0) {
    echo "Username already exists.";
    exit();
}

$result = mysqli_query($con, "INSERT INTO accounts (username, password) VALUES ('$username', '$password')");

if ($result) {
    echo "Account successfully registered!";
} else {
    echo "Error: " . mysqli_error($con);
}

?>
