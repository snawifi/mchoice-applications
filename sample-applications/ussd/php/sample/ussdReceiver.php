<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/

include_once '../api/UssdMessageSender.php';
include_once '../api/UssdMessageReceiver.php';
include_once 'logs.php';


try{
	//create the receiver
	$reciever=new UssdMessageReceiver();
	//getting the message content
	$rtn="{$reciever->getAddress()} :: {$reciever->getMessage()} :: {$reciever->getCorrelator()}";
	logFile($rtn);

	/*
	creating the sender object with
		server url
		appId
		password
	*/
	$sender=new UssdMessageSender("http://localhost:8080/appzone-simulator/simulator", "PD_ET_e0550", "098f6bcd4621d373cade4e832627b4f6");

    $res='';
	//sending a message
	if($reciever->getMessageType()== 'X-USSD-Message') {
        $res=$sender->TerminateUssd('0771448890', 'Test Terminate', '567', '34447');
    } else if($reciever->getMessageType()=='X-USSD-Alive-Message'){
        $res=$sender->AliveUssd();
    } else {
        $res=$sender->normalUssd('0771238890', 'Test Normal', '234', '34567');
    }
    logFile($res);

}
catch(AppZoneException $ex){
	//throws when failed sending or receiving the sms
	logFile("ERROR: {$ex->getStatusCode()} | {$ex->getStatusMessage()}");
}
