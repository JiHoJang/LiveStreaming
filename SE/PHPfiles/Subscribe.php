<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);
if(!$con) {
        echo "[ERROR] Cannot connect to database.";
        exit;
}

$lectureName = $_POST['lectureName'];
$studentEmail = $_POST['studentEmail'];


$Sql_Query2 = "update lecture set num_student = num_student + 1 where title = '".$lectureName."';";

$result2 = mysqli_query($con, $Sql_Query2);
if(!$result2) {
        exit;
}

$Sql_Query3 = "insert into subscribe (lecture) select l.SN from lecture l where l.title = '".$lectureName."';";
$result3 = mysqli_query($con, $Sql_Query3);

if(!$result3) {
        exit;
}

$Sql_Query4 = "update subscribe set student = (select s.SN from student s where s.email = '".$studentEmail."') where lecture = (select l.SN from lecture l where l.title = '".$lectureName."');";

$result4 = mysqli_query($con, $Sql_Query4);

if(!$result4) {
        echo "[ERROR] Cannot run query";
        exit;
} else {
        echo "Success";
}


mysqli_close($con);
?>
