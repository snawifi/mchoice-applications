<?php
/*
* $LastChangedDate$
* $LastChangedBy$
* $LastChangedRevision$
*/
 
class UssdMessageReceiver {
    private $address;
	private $message;
	private $messageType;
	private $correlator;

	public function __construct(){
        $this->messageType=(isset($_SERVER['HTTP_X_MESSAGE_TYPE']))?$_SERVER['HTTP_X_MESSAGE_TYPE']:null;

		$this->address=(isset($_POST['address']))?$_POST['address']:null;
		$this->message=(isset($_POST['message']))?$_POST['message']:null;
		$this->correlator=(isset($_POST['correlatorId']))?$_POST['correlatorId']:null;

		if(!((isset($this->address) && isset($this->message) && isset($this->correlator)))){
			throw new Exception("Some of the required parameters are not provided");
		}
	}    

	public function getAddress(){
		return $this->address;
	}

	public function getMessage(){
		return $this->message;
	}

    public function getMessageType(){
		return $this->messageType;
	}

	public function getCorrelator(){
		return $this->correlator;
	}



}
