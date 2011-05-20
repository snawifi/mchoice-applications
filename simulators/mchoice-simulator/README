##########################################################
#################   mChoice Simulator    #################
##########################################################

1. Required
	Java 1.6

2. To run the simulator	Execute the Following command
         start-simulator.bat if windows or start-simulator.sh if linux

3. Test an application using the simulator

	To test ussd applications set the following URL in your application.
		http://127.0.0.1:8000/ussd/

 	To test sms applications set the following URL in your application.
		http://127.0.0.1:8000/sms/

4. Monitor an application using simulator's interactive UI

  	 Simulator is running on
  		http://127.0.0.1:8000/simulator/

    This is the interactive UI where you can monitor the message sending/receiving to/from the application.

    To test a sms application use the input fields inside the SMS Tab
	To test a ussd application use input fields the inside the USSD Tab

	Enter your application url into the App URL field
	Enter the phone number into the Phone # field
	Type the message inside the message field
	Click on the send button to send the message to the application

5. Response messages

    SMS
        SBL-SMS-MT-2000, SUCCESS                                                              -> success message
        ADDRESS-NOT-SPECIFIED, Check if the address has been correctly set                    -> if address is missing
        UNAUTHORIZED-REQUEST, Request could not be authenticated                              -> if appid/password missing
        400, Bad Request [ Couldn't find parameter (message) in the request or it is blank ]  -> if message field is blank

    USSD
        SBL-USSD-2000, Sucess                                                                 -> success message
        ERROR, Unautorized Request , AppId or Password Missing                                -> if appid/password missing
        ERROR, Address not specified                                                          -> if address is missing
        ERROR, Address not found                                                              -> if address is incorrect
        ERROR, Incorrect Conversation Id                                                      -> if conversation id is incorrect

