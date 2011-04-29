<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/

class UssdMessageSender {
    var $url;
    var $username;
	var $password;

    public function __construct($url, $username, $password) {
        $this->url = $url;
        $this->username=$username;
		$this->password=$password;
    }

    public function sendussd($address, $message, $conversationId,  $sessioTermination=false) {
        $headers =array('Content-type: application/json',
                'X-Message-type: X-USSD-Message', 'X-Requested-Shortcode: 4499',
                'X-Requested-Conversation-ID:'.$conversationId, 'X-Requested-Version: 1.0', $this->getAuthHeader());

        $postfields = "address=" . urlencode($address) . "&message=" . urlencode($message) . "&sessionTermination". $sessioTermination;

        return $this->sendRequest($postfields, $headers);
    }

    //sending normal ussd messages
    public function normalUssd($address, $message, $correlationId, $conversationId) {
        $headers = array('Content-type: application/json',
                'X-Message-type: X-USSD-Message', 'X-Requested-Shortcode: 4499',
                'X-Requested-Conversation-ID:'.$conversationId, 'X-Requested-Version: 1.0');

        $postfields = "correlationId=" .$correlationId. "&address=" . urlencode($address) . "&message=" . urlencode($message);

        $this->sendRequest($postfields, $headers);
    }

    //sending terminate ussd messages
    public function TerminateUssd($address, $message, $correlationId, $conversationId) {
        $headers = array('X-Message-type: X-USSD-Terminate-Message',
                'X-Requested-Conversation-ID:'.$conversationId, 'X-Requested-Version: 1.0');

        $postfields = "correlationId=" .$correlationId. "&address=" . urlencode($address) . "&message=" . urlencode($message);

        $this->sendRequest($postfields, $headers);
    }

    //sending alive ussd messages
    public function AliveUssd() {
        $headers = array('X-Message-type: X-USSD-Alive-Message', 'X-Requested-Version: 1.0');

        $postfields = '';

        $this->sendRequest($postfields, $headers);
    }

    private function getAuthHeader(){
		$auth=$this->username . ':' . $this->password;
		$auth=base64_encode($auth);
		return 'Authorization: Basic ' . $auth;
	}

    private function sendRequest($postfields, $header) {
        $ch = curl_init($this->url);

        // Configuring curl options
        $options = array(
            CURLOPT_HTTPHEADER => $header,
            CURLOPT_POST => true,
            CURLOPT_POSTFIELDS => array('json' => json_encode($postfields)),
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_FOLLOWLOCATION => true
        );

        curl_setopt_array($ch, $options);
        $result = curl_exec($ch);
        curl_close($ch);
        $this->handleResponse($result);
    }

    private function handleResponse($result){
		if ($result == "") {
            throw new AppZoneException
            ("Server URL is invalid", '500');
        } else if ($result->status_code == 'SBL-USSD-2000') {
            return true;
        } else {
            throw new AppZoneException
            ($result->status_message, $result->status_code, $result);
        }
	}
}

class AppZoneException extends Exception {
    var $code;
    var $response;
    var $statusMessage;

    public function __construct($message, $code, $response = null) {
        parent::__construct($message);
        $this->statusMessage = $message;
        $this->code = $code;
        $this->response = $response;
    }

    public function getStatusCode() {
        return $this->code;
    }

    public function getStatusMessage() {
        return $this->statusMessage;
    }

    public function getRawResponse() {
        return $this->response;
    }
}
