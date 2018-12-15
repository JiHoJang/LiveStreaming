<?php
include 'DatabaseConfig.php';
$keyword = $_POST["keyword"];

$con = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

if($con->connect_error){
        die("Connection failed: ". $conn->connect_error);
}

$sql = "SELECT title, instructor, num_student, lecture_eval, lecture_feature FROM lecture where
                title like '%".$keyword."%' or instructor like '%".$keyword."%';";

$result = $con->query($sql);


if ($result->num_rows>0) {
        $data = array();
        while($row = $result->fetch_assoc()) {
                $data[] = json_encode($row);
        }
        echo json_encode($data);
} else {
        echo "No result";
}

$con->close();
?>
