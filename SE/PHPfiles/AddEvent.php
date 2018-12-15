<?php
include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);
if(!$con) {
        echo "[ERROR] Cannot connect to database.";
        exit;
}

$lectureSN = $_POST['lectureSN'];
$lectureDate = $_POST['lectureDate'];


$Sql_Query = "update lecture set date = '$lectureDate' where SN='$lectureSN';";

$result = mysqli_query($con, $Sql_Query);

if(!$result) {
    echo "[ERROR] Cannot run query.";
    exit;
} else {
    echo "Success";
}
mysqli_close($con);
?>
