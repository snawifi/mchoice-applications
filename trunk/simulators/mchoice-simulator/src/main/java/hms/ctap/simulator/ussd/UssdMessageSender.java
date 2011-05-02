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
import hms.sdp.ussd.impl.UssdTerminateMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.jetty.http.HttpHeaders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class UssdMessageSender {

    private static UssdMessageSender ussdMessageSender;

    private Map<String, Conversation> conversationMap;
    private HttpClient httpClient;
    private ScheduledExecutorService executorService;

    private UssdMessageSender() {
        httpClient = new HttpClient();
    }

    public void sendMessage(String url, String address, String message) throws IOException {

        final PostMethod postMethod = createPostClient(url, address, MchoiceUssdMessage.USSD_MESSAGE);
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

    private PostMethod createPostClient(String url, String address, String messageType) {
        final PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        postMethod.addRequestHeader(MchoiceUssdMessage.USSD_MESSAGE_TYPE, messageType);
        postMethod.addRequestHeader(MchoiceUssdMessage.CONVERSATION, checkAddNewConversation(address, url));
        postMethod.addRequestHeader(MchoiceUssdMessage.REQUEST_VERSION, "1.0");
        postMethod.addRequestHeader("X-Requested-Shortcode", "#141#1000");
        return postMethod;
    }

    private String checkAddNewConversation(String address, String url) {
        Conversation conversationId = conversationMap.get(address);
        if (conversationId == null) {
            conversationId = new Conversation(String.valueOf(Math.random() * 10000000000l), url);
            conversationMap.put(address, conversationId);
        }
        return conversationId.getConversationId();
    }

    public boolean clearConversation(String address) {
        return conversationMap.remove(address) != null;
    }

    public boolean isConversationIdValid(String address, String conversationId) {
        return (conversationMap.get(address) != null &&
                conversationMap.get(address).getConversationId().contentEquals(conversationId));
    }

    public static UssdMessageSender getInstance() {
        if (ussdMessageSender == null) {
            ussdMessageSender = new UssdMessageSender();
            final Map<String, Conversation> hashMap = new ConcurrentHashMap<String, Conversation>();
            ussdMessageSender.conversationMap = hashMap;
            ussdMessageSender.executorService = Executors.newSingleThreadScheduledExecutor();
            ussdMessageSender.executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        execute(ussdMessageSender, hashMap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 180, 60, TimeUnit.SECONDS);
        }
        return ussdMessageSender;
    }

    private static void execute(UssdMessageSender ussdMessageSender, Map<String, Conversation> hashMap) throws IOException {
        if (!hashMap.isEmpty()) {
            for (Map.Entry<String, Conversation> entry : hashMap.entrySet()) {
                final PostMethod postMethod =
                        ussdMessageSender.createPostClient(entry.getValue().getUrl(), entry.getKey(),
                        MchoiceUssdMessage.USSD_TERMINATE_MESSAGE);
                final UssdTerminateMessage ussdMessage = new UssdTerminateMessage();
                ussdMessage.setCorrelationId("89898989898");
                ussdMessage.setAddress(entry.getKey());
                postMethod.setRequestBody(new Gson().toJson(ussdMessage));
                hashMap.remove(entry.getKey());
                ussdMessageSender.httpClient.executeMethod(postMethod);
            }
        }
    }

    private class Conversation {

        private String conversationId;
        private String url;

        private Conversation(String conversationId, String url) {
            this.conversationId = conversationId;
            this.url = url;
        }

        public String getConversationId() {
            return conversationId;
        }

        public void setConversationId(String conversationId) {
            this.conversationId = conversationId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
