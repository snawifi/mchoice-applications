<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/


class UssdMessageSender
{
    var $url;
    var $username;
    var $password;

    /*
     * Creating the sender object
     * $url - sender url
     * $username
     * $password
     */
    public function __construct($url, $username, $password)
    {
        $this->url = $url;
        $this->username = $username;
        $this->password = $password;
    }

    /*
     * Sends the ussd message
     * $address - Address of the sender(phone number)
     * $message
     * $conversationId - this should be taken from the received request
     * $sessionTermination - must be set to true or false
     */
    public function sendUssd($address, $message, $conversationId, $sessionTermination = false)
    {
        $headers = array(
            'Content-type: application/json',
            'X-Requested-Encoding: UTF-8',
            'X-Requested-Conversation-ID:' . $conversationId,
            'X-Requested-Version: 1.0',
            $this->getAuthHeader());

        $postData = array('address' => $address, 'message' => $message, 'sessionTermination' => $sessionTermination);

        return $this->sendRequest($postData, $headers);
    }

    private function getAuthHeader()
    {
        $auth = $this->username . ':' . $this->password;
        $auth = base64_encode($auth);
        return 'Authorization: Basic ' . $auth;
    }

    /*
     * Creates the JSON object that's sent using cURL
     * $postData - request body
     * $header - request header
     */
    private function sendRequest($postData, $header)
    {
        $ch = curl_init($this->url);

        // Configuring curl options
        $options = array(
            CURLOPT_HTTPHEADER => $header,
            CURLOPT_POST => 1,
            CURLOPT_POSTFIELDS => json_encode($postData),
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_FOLLOWLOCATION => true
        );

        curl_setopt_array($ch, $options);
        $result = curl_exec($ch);
        curl_close($ch);
        return $this->handleResponse($result);
    }

    /*
     * Handles the response message 
     */
    private function handleResponse($result)
    {
        $resp = json_decode($result);

        if ($result == "") {
            throw new AppZoneException
            ("Server URL is invalid", '500');
        } else if ($resp->{'statusCode'} == 'SBL-USSD-2000') {
            return $resp;
        } else {
            throw new AppZoneException($resp->{'statusDescription'}, $resp->{'statusCode'}, $resp);
        }
    }
}

class AppZoneException extends Exception
{
    var $code;
    var $response;
    var $statusMessage;

    public function __construct($message, $code, $response = null)
    {
        parent::__construct($message);
        $this->statusMessage = $message;
        $this->code = $code;
        $this->response = $response;
    }

    public function getStatusCode()
    {
        return $this->code;
    }

    public function getStatusMessage()
    {
        return $this->statusMessage;
    }

    public function getRawResponse()
    {
        return $this->response;
    }
}
