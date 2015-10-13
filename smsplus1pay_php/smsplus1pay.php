<?php
$arParams['access_key'] = $_GET['access_key'] ? $_GET['access_key'] : '';
   $arParams['command_code'] = $_GET['command_code'] ? $_GET['command_code'] : '';
   $arParams['mo_message'] = $_GET['mo_message'] ? $_GET['mo_message'] : '';
   $arParams['msisdn'] = $_GET['msisdn'] ? $_GET['msisdn'] : '';
   $arParams['request_id'] = $_GET['request_id'] ? $_GET['request_id'] : '';
   $arParams['request_time'] = $_GET['request_time'] ? $_GET['request_time'] : '';
   $arParams['amount'] = $_GET['amount'] ? $_GET['amount'] : '';
   $arParams['signature'] = $_GET['signature'] ? $_GET['signature'] : '';
   $arParams['error_code'] = $_GET['error_code'] ? $_GET['error_code'] : '';
   $arParams['error_message'] = $_GET['error_message'] ? $_GET['error_message'] : '';
   $data = "access_key=" . $arParams['access_key'] . "&amount=" . $arParams['amount'] . "&command_code=" . $arParams['command_code'] . "&error_code=" . $arParams['error_code'] . "&error_message=" . $arParams['error_message'] . "&mo_message=" . $arParams['mo_message'] . "&msisdn=" . $arParams['msisdn'] . "&request_id=" . $arParams['request_id'] . "&request_time=" . $arParams['request_time'];
   $secret = ''; //product's secret key (get value from 1Pay product detail)
   $signature = hash_hmac("sha256", $data, $secret); // create signature to check
   $arResponse['type'] = 'text';
   // kiem tra signature neu can
   if ($arParams['signature'] == $signature) {
    //if sms content and amount and ... are ok. return success case
    $arResponse['status'] = 1;
    $arResponse['sms'] = 'Giao dich thanh cong ... Lien he ... de biet them chi tiet';
   }
   else {
    //if not. return fail case
	$arResponse['status'] = 0;
    $arResponse['sms'] = 'Giao dich khong thanh cong. Lien he ... de biet them chi tiet.';
   }
   // return json for 1pay system
   echo json_encode($arResponse);

?>