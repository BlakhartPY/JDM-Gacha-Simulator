<?php
// Enable error reporting
error_reporting(E_ALL);
ini_set('display_errors', 1);

include_once('connects.php');

if (isset($_POST['user_id']) && isset($_POST['card_name'])) {
    $user_id = $_POST['user_id'];
    $card_name = $_POST['card_name'];

    // Check if the card already exists for this user
    $checkQuery = "SELECT count FROM collections WHERE user_id = ? AND card_name = ?";
    $stmt = $con->prepare($checkQuery);
    $stmt->bind_param("is", $user_id, $card_name);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        // Card exists, update count
        $stmt->bind_result($count);
        $stmt->fetch();
        $stmt->close();

        $updateQuery = "UPDATE collections SET count = count + 1 WHERE user_id = ? AND card_name = ?";
        $updateStmt = $con->prepare($updateQuery);
        $updateStmt->bind_param("is", $user_id, $card_name);
        if ($updateStmt->execute()) {
            echo "Card count updated";
        } else {
            echo "Error updating card count";
        }
        $updateStmt->close();
    } else {
        // Card does not exist, insert new
        $stmt->close();
        $insertQuery = "INSERT INTO collections (user_id, card_name, count) VALUES (?, ?, 1)";
        $insertStmt = $con->prepare($insertQuery);
        $insertStmt->bind_param("is", $user_id, $card_name);
        if ($insertStmt->execute()) {
            echo "Card inserted";
        } else {
            echo "Error inserting card";
        }
        $insertStmt->close();
    }
} else {
    echo "Missing parameters";
}
?>
