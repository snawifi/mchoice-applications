<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/

define("NORMAL_MESSAGE", "X-USSD-Message");
define("TERMINATE_MESSAGE", "X-USSD-Terminate-Message");
define("ALIVE_MESSAGE", "X-USSD-Alive-Message");

class UssdMessageReceiver {


    private $address;
    private $message;
    private $messageType;
    private $correlationId;
    private $conversationId;

    public function __construct() {
        $arrHeaders = $this->getHeaders();

        $this->messageType = $arrHeaders['X-Message-Type'];
        $this->conversationId = $arrHeaders['X-Requested-Conversation-Id'];

        ///read the request body
        $body = @file_get_contents('php://input');

        $json = json_decode($body);

        //If the message type is ALIVE sends the Alive message
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

    /*
     * Read the request Header
     */
    function getHeaders() {
        $headers = array();
        foreach ($_SERVER as $k => $v) {
            if (substr($k, 0, 5) == "HTTP_") {
                $k = str_replace('_', ' ', substr($k, 5));
                $k = str_replace(' ', '-', ucwords(strtolower($k)));
                $headers[$k] = $v;
            }
        }
        return $headers;
    }

    public function getAddress() {
        return $this->address;
    }

    public function getMessage() {
        return $this->message;
    }

    public function getMessageType() {
        return $this->messageType;
    }

    public function getCorrelationId() {
        return $this->correlationId;
    }

    public function getConversationId() {
        return $this->conversationId;
    }


}
