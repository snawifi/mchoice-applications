<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/
    include_once '../api/UssdMessageSender.php';

    $sender=new UssdMessageSender('http://127.0.0.1:8000/ussd/', 'appid', 'pass');

    try{
		//sending a one message
		var_dump( $sender->sendussd('0771234455', 'Test Message', '12345', true));
		echo "<br>";

	}
	catch(AppZoneException $ex){
		//throws when failed sending or receiving the sms
		echo "ERROR::{$ex->getStatusMessage()}";
	}
