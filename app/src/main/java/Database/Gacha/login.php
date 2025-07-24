<?php
include_once("connects.php");

$username = $_POST['username'];
$password = $_POST['password'];

if (empty($username) || empty($password)) {
    echo "All fields are required.";
    exit();
}

$query = mysqli_query($con, "SELECT * FROM accounts WHERE username = '$username'");
$user = mysqli_fetch_assoc($query);

if ($user && $password === $user['password']) {
    echo "Login successful|" . $user['id'];  // Return success + ID separated by a pipe
} else {
    echo "Invalid username or password";
}
?>
