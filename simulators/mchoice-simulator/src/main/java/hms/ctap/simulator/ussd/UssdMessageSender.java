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
package hms.ctap.simulator.ussd;

import com.google.gson.Gson;
import hms.sdp.ussd.MchoiceUssdMessage;
import hms.sdp.ussd.impl.UssdAtRequestMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class UssdMessageSender {

    private static UssdMessageSender ussdMessageSender = new UssdMessageSender();
    private HttpClient httpClient;

    private UssdMessageSender () {
        httpClient = new HttpClient();
    }

    public void sendMessage (String url, String address, String message) throws IOException {
        final Gson gson = new Gson();
        final PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader(MchoiceUssdMessage.USSD_MESSAGE_TYPE, MchoiceUssdMessage.USSD_MESSAGE);
        postMethod.addRequestHeader(MchoiceUssdMessage.CONVERSATION, String.valueOf(Math.random()));
        postMethod.addRequestHeader("X-Requested-Shortcode", "4499");
        postMethod.addRequestHeader(MchoiceUssdMessage.REQUEST_VERSION, "1.0");
        final UssdAtRequestMessage ussdAtRequestMessage = new UssdAtRequestMessage();
        ussdAtRequestMessage.setMessage(message);
        ussdAtRequestMessage.setAddress(address);
        ussdAtRequestMessage.setCorrelationId(String.valueOf(Math.random()));

        final String jsonReq = gson.toJson(ussdAtRequestMessage);
        postMethod.setRequestBody(jsonReq);
        System.out.println("Sending USSD to application [" + jsonReq + "]");
        httpClient.executeMethod(postMethod);
        System.out.println(postMethod.getResponseBodyAsString());
    }

    public static UssdMessageSender getInstance() {
        if (ussdMessageSender == null) {
            ussdMessageSender = new UssdMessageSender();
        }
        return ussdMessageSender;
    }
}
