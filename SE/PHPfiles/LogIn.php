<?php
include 'DatabaseConfig.php';
$email = $_POST["email"];
$password = $_POST["password"];

$con = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

if ($con->connect_error) {
        die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM student where
                email = '".$email."' and pw = '".$password."';";

$result = $con->query($sql);

if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $json = json_encode($row);
        //echo $json;
        echo "Success";
} else {
        echo "Check your email and password";
}

$con->close();
?>
