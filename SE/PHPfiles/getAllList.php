<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);
if(!$con) {
        echo "[ERROR] Cannot connect to database.";
        exit;
}

$Query = "select * from lecture;";

$result = mysqli_query($con, $Query);

if(!$result) {
        echo "[ERROR] Cannot run query.";
    exit;
}

$response = array();

while($row = mysqli_fetch_array($result)) {
    array_push($response, array("SEND_SN"=>$row['SN'],"SEND_TITLE"=>$row['title'], "SEND_INSTRUCTOR"=>$row['instructor'],"SEND_PRICE"=>$row['price'],"SEND_MAX_STUDENT"=>$row['max_student'],"SEND_NUM_STUDENT"=>$row['num_student'],"SEND_LECTURE_FEATURE"=>$row['lecture_feature']));
   }

mysqli_close($con);

echo json_encode(array("server_response"=>$response));

?>
