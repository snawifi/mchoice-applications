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
    logFile("\nMessage Received...");
    //getting the message content
    $rtn = "{$receiver->getAddress()} :: {$receiver->getMessage()} :: {$receiver->getConversationId()}";
    logFile("Message: ". $rtn);



    $res = '';
    //sending a message
    if ($receiver->getMessageType() == NORMAL_MESSAGE) {
        $sender = new UssdMessageSender("http://192.168.0.169:8000/ussd/", "appid", "pass");
        $res = $sender->sendUssd('0771448890', 'Thank You for your message', $receiver->getConversationId(), 'false');

    } else if ($receiver->getMessageType() == TERMINATE_MESSAGE) {
        logFile("Terminate message received address : ".$receiver->getAddress()." conversationId : ".$receiver->getConversationId());
    }

//    logFile($res);
    logFile("\nRESPONSE::: correlationId :".$res->{'correlationId'}.", statusCode :".$res->{'statusCode'}.", statusDescription :".$res->{'statusDescription'});

}
catch (AppZoneException $ex) {
    //throws when failed sending or receiving the message
    logFile("ERROR: $ex");
}
