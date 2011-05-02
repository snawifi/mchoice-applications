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
import hms.sdp.ussd.MchoiceUssdResponse;
import hms.sdp.ussd.impl.UssdAoRequestMessage;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class UssdMessageReceiver extends HttpServlet {

    private static final List<UssdAoRequestMessage> receivedMessages = new ArrayList<UssdAoRequestMessage>();
    private static final String AUTHORIZATION = "Authorization";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String jsonResponse = handleRequest(req);
        resp.getWriter().write(jsonResponse);
        resp.getWriter().flush();
    }

    private String handleRequest(HttpServletRequest req) throws IOException {

        Gson gson = new Gson();
        final UssdAoRequestMessage ussdAoRequestMessage = gson.fromJson(readBody(req.getInputStream()), UssdAoRequestMessage.class);
        final String conversationId = req.getHeader(MchoiceUssdMessage.CONVERSATION);
        ussdAoRequestMessage.setConversationId(conversationId);

        System.out.println("New USSD Message Received [" + ussdAoRequestMessage + "]");

        final MchoiceUssdResponse mchoiceUssdResponse = new MchoiceUssdResponse();
        try {
            validateAndSetAuthentication(req, mchoiceUssdResponse);
            validateAndSetAddress(ussdAoRequestMessage, mchoiceUssdResponse);
            validateAndSetConversationId(ussdAoRequestMessage, mchoiceUssdResponse);

        } catch (Exception e) {
            System.out.println("Received USSD Message Failed : "+ e );
            System.out.println(ussdAoRequestMessage);
        }
        mchoiceUssdResponse.setCorrelationId(String.valueOf(Math.random()));
        return gson.toJson(mchoiceUssdResponse);
    }

    private void validateAndSetAuthentication(HttpServletRequest req, MchoiceUssdResponse mchoiceUssdResponse) throws Exception {
        try {
            String decoded = new String(Base64.decodeBase64(req.getHeader(AUTHORIZATION).substring(6).getBytes()));
            String authentication[] = decoded.split(":");
            for (String s : authentication) {
                if ("".equals(s)) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            createFailedResponse(mchoiceUssdResponse, "Unauthorized Request");
            throw new Exception("Unautorized Request , Incorrect AppId or Password");
        }

    }

    private void validateAndSetAddress(UssdAoRequestMessage ussdAoRequestMessage, MchoiceUssdResponse mchoiceUssdResponse) throws Exception {
        if ("".equals(ussdAoRequestMessage.getAddress())) {
            createFailedResponse(mchoiceUssdResponse, "Address not specified");
            throw new Exception("Address not specified");
        }

    }


    private void validateAndSetConversationId(UssdAoRequestMessage ussdAoRequestMessage, MchoiceUssdResponse mchoiceUssdResponse) throws Exception {
        final UssdMessageSender messageSender = UssdMessageSender.getInstance();

        if (ussdAoRequestMessage.getSessionTermination()) {
            if (messageSender.clearConversation(ussdAoRequestMessage.getAddress())) {
                createSuccessResponse(mchoiceUssdResponse);
                receivedMessages.add(ussdAoRequestMessage);
            } else {
                createFailedResponse(mchoiceUssdResponse, "Address not found");
                throw new Exception("Address not found");
            }
        } else if (messageSender.isConversationIdValid(ussdAoRequestMessage.getAddress(), ussdAoRequestMessage.getConversationId())) {
            receivedMessages.add(ussdAoRequestMessage);
            createSuccessResponse(mchoiceUssdResponse);
        } else {
            createFailedResponse(mchoiceUssdResponse, "Conversation Id incorrect");
            throw new Exception("Conversation Id incorrect");
        }
    }

    private void createFailedResponse(MchoiceUssdResponse mchoiceUssdResponse, String statusDescription) {
        mchoiceUssdResponse.setStatusCode("ERROR");
        mchoiceUssdResponse.setStatusDescription(statusDescription);
    }

    private void createSuccessResponse(MchoiceUssdResponse mchoiceUssdResponse) {
        mchoiceUssdResponse.setStatusCode("SBL-USSD-2000");
        mchoiceUssdResponse.setStatusDescription("Success");
    }

    private String readBody(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder line = new StringBuilder();
        String li;
        while ((li = in.readLine()) != null) {
            line.append(li);
        }
        return line.toString();
    }

    public static List<UssdAoRequestMessage> getReceivedMessages() {
        return receivedMessages;
    }
}
