<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);
if(!$con) {
        echo "[ERROR] Cannot connect to database.";
        exit;
}

$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];

$Sql_Query = "select count(*) from student where email = '".$email."'";

$result = mysqli_query($con, $Sql_Query);
if(!$result) {
        echo "[ERROR] Cannot run query.";
        exit;
}

$rowchk = mysqli_fetch_row($result);
$count = $rowchk[0];

if($count == 0) {

        $Sql_Query = "insert into student (email, name, pw) values('$email', '$name', '$password');";

        $result = mysqli_query($con, $Sql_Query);
        if(!$result) {
                echo "[ERROR] Cannot run query2.";
                exit;
        } else {
                echo "Success";
        }
} else {
        echo "Email Overlap Try Again";
}

mysqli_close($con);
?>
