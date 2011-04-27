/*
 *   (C) Copyright 2010-2011 hSenid Software International (Pvt) Limited.
 *   All Rights Reserved.
 *
 *   These materials are unpublished, proprietary, confidential source code of
 *   hSenid Software International (Pvt) Limited and constitute a TRADE SECRET
 *   of hSenid Software International (Pvt) Limited.
 *
 *   hSenid Software International (Pvt) Limited retains all title to and intellectual
 *   property rights in these materials.
 *
 */
package hms.ctap.simulator.sms;


import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class SmsMessageSender {
    private static SmsMessageSender smsMessageSender = new SmsMessageSender();
    private HttpClient httpClient;

    private SmsMessageSender() {
        httpClient = new HttpClient();
    }

    public void sendMessage (String url, String address, String message) throws IOException {

        final PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("CONTENT_TYPE", "application/x-www-form-urlencoded");
        postMethod.addRequestHeader("ACCEPT", "text/xml");
        postMethod.addParameter("version", "1.0");
        postMethod.addParameter("address",address);
        postMethod.addParameter("message", message);
        String correlator = String.valueOf(Math.random());
        postMethod.addParameter("correlator", correlator);
        System.out.println("Sending SMS to application [ address: "+ address + " ,message: "+message + " ,correlator: "+ correlator + " ]");
        httpClient.executeMethod(postMethod);
        System.out.println(postMethod.getResponseBodyAsString());

    }

    public static SmsMessageSender getInstance() {

        if (smsMessageSender == null) {
            smsMessageSender = new SmsMessageSender();
        }
        return smsMessageSender;
    }
}
