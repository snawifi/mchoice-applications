<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/

define("NORMAL_MESSAGE", "X-USSD-Message");
define("TERMINATE_MESSAGE", "X-USSD-Terminate-Message");
define("ALIVE_MESSAGE", "X-USSD-Alive-Message");

class UssdMessageReceiver
{


    private $address;
    private $message;
    private $messageType;
    private $correlationId;
    private $conversationId;

    public function __construct()
    {
        $this->messageType = (isset($_SERVER['X-Message-type'])) ? $_SERVER['X-Message-type'] : null;
        $this->conversationId = (isset($_SERVER['X-Requested-Conversation-ID'])) ? $_SERVER['X-Requested-Conversation-ID'] : null;

        $body = http_get_request_body();
        $json = json_decode($body);

        if ($this->messageType == ALIVE_MESSAGE) {
            header("HTTP/1.1 202 Accepted");
            return;
        }

        $this->address = $json->{'address'};
        $this->message = $json->{'message'};
        $this->correlationId = $json->{'correlationId'};

        if (!((isset($this->address) && isset($this->correlationId)))) {
            throw new Exception("Some of the required parameters are not provided");
        }
    }

    public function getAddress()
    {
        return $this->address;
    }

    public function getMessage()
    {
        return $this->message;
    }

    public function getMessageType()
    {
        return $this->messageType;
    }

    public function getCorrelationId()
    {
        return $this->correlationId;
    }

    public function getConversationId()
    {
        return $this->conversationId;
    }


}
