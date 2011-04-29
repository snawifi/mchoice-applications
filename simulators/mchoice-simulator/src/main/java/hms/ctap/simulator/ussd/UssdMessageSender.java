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
import org.eclipse.jetty.http.HttpHeaders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class UssdMessageSender {

    private static UssdMessageSender ussdMessageSender;

    private Map<String, String> conversationMap;
    private HttpClient httpClient;

    private UssdMessageSender () {
        httpClient = new HttpClient();
    }

    public void sendMessage (String url, String address, String message) throws IOException {

        final PostMethod postMethod = createMethod(url, address);
        final UssdAtRequestMessage ussdAtRequestMessage = new UssdAtRequestMessage();
        ussdAtRequestMessage.setMessage(message);
        ussdAtRequestMessage.setAddress(address);
        ussdAtRequestMessage.setCorrelationId(String.valueOf(Math.random()));

        final Gson gson = new Gson();
        final String jsonReq = gson.toJson(ussdAtRequestMessage);
        postMethod.setRequestBody(jsonReq);
        System.out.println("Sending USSD to application [" + jsonReq + "]");
        httpClient.executeMethod(postMethod);
        System.out.println(postMethod.getResponseBodyAsString());
    }

    private PostMethod createMethod(String url, String address) {
        final PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        postMethod.addRequestHeader(MchoiceUssdMessage.USSD_MESSAGE_TYPE, MchoiceUssdMessage.USSD_MESSAGE);
        postMethod.addRequestHeader(MchoiceUssdMessage.CONVERSATION, checkAddNewConversation(address));
        postMethod.addRequestHeader(MchoiceUssdMessage.REQUEST_VERSION, "1.0");
        postMethod.addRequestHeader("X-Requested-Shortcode", "#141#1000");
        return postMethod;
    }

    private String checkAddNewConversation(String address) {
        String conversationId = conversationMap.get(address);
        if (conversationId == null) {
            conversationId = String.valueOf(Math.random() * 10000000000l);
            conversationMap.put(address, conversationId);
        }
        return conversationId;
    }

    public boolean clearConversation(String address) {
        return conversationMap.remove(address) != null;
    }

    public boolean isConversationIdValid(String address) {
        return conversationMap.get(address) != null;
    }

    public static UssdMessageSender getInstance() {
        if (ussdMessageSender == null) {
            ussdMessageSender = new UssdMessageSender();
            ussdMessageSender.conversationMap = new HashMap<String, String>();
        }
        return ussdMessageSender;
    }
}
