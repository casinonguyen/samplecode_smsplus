<?php
   $arParams['access_key'] = $_GET['access_key'] ? $_GET['access_key'] : '';
   $arParams['amount'] = $_GET['amount'] ? $_GET['amount'] : '';
   $arParams['command_code'] = $_GET['command_code'] ? $_GET['command_code'] : '';
   $arParams['mo_message'] = $_GET['mo_message'] ? $_GET['mo_message'] : '';
   $arParams['msisdn'] = $_GET['msisdn'] ? $_GET['msisdn'] : '';
   $arParams['telco'] = $_GET['telco'] ? $_GET['telco'] : '';
   $arParams['signature'] = $_GET['signature'] ? $_GET['signature'] : '';
   $data = "access_key=" . $arParams['access_key'] . "&amount=" . $arParams['amount'] . "&command_code=" . $arParams['command_code'] . "&mo_message=" . $arParams['mo_message'] . "&msisdn=" . $arParams['msisdn'] . "&telco=" . $arParams['telco'];
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