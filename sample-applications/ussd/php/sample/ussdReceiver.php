<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/

include_once '../api/UssdMessageSender.php';
include_once '../api/UssdMessageReceiver.php';
include_once 'logs.php';


try {
    //create the receiver
    $receiver = new UssdMessageReceiver();
    //getting the message content
    $rtn = "{$receiver->getAddress()} :: {$receiver->getMessage()} :: {$receiver->getConversationId()}";
    logFile($rtn);

    /*
     creating the sender object with
         server url
         appId
         password
     */
    $sender = new UssdMessageSender("http://localhost:8080/appzone-simulator/simulator", "PD_ET_e0550", "098f6bcd4621d373cade4e832627b4f6");

    $res = '';
    //sending a message
    if ($receiver->getMessageType() == NORMAL_MESSAGE) {
        $res = $sender->sendUssd('0771448890', 'Test Terminate', '567', '34447');
    } else if ($receiver->getMessageType() == TERMINATE_MESSAGE) {
        logFile("Terminate message received address : ".$receiver->getAddress()." conversationId : ".$receiver->getConversationId());
    }

    logFile($res);

}
catch (AppZoneException $ex) {
    //throws when failed sending or receiving the sms
    logFile("ERROR: {$ex->getStatusCode()} | {$ex->getStatusMessage()}");
}
