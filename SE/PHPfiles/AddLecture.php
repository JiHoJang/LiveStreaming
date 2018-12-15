<?php
include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);
if(!$con) {
        echo "[ERROR] Cannot connect to database.";
        exit;
}

$lectureName = $_POST['lectureName'];
$lecturePrice = $_POST['lecturePrice'];
$numPeople = $_POST['numPeople'];
$lecturerName = $_POST['lecturerName'];
$info = $_POST['info'];


$Sql_Query = "insert into lecture (title, instructor, price, max_student, num_student, lecture_feature) values('$lectureName', '$lecturerName','$lecturePrice', '$numPeople', 0, '$info');";

$result = mysqli_query($con, $Sql_Query);

if(!$result) {
    echo "[ERROR] Cannot run query.";
    exit;
} else {
    echo "Success";
}
mysqli_close($con);
?>
