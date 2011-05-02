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


import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class SmsMessageReceiver extends HttpServlet {

    private static final List<SmsAoRequestMessage> receivedSms = new ArrayList<SmsAoRequestMessage>();
    private static final String AUTHORIZATION = "Authorization";
    private static final String ADDRESS = "address";
    private static final String MESSAGE = "message";
    private static final String RESPONSE_MESSAGE = "<mchoice_sdp_sms_response>\n" +
            "   <version>1.0</version>\n" +
            "   <correlator>{0}</correlator>\n" +
            "   <status_code>{1}</status_code>\n" +
            "   <status_message>{2}</status_message>\n" +
            "</mchoice_sdp_sms_response>";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            handleSmsRequest(req, resp);
            writeResponse(resp, "SBL-SMS-MT-2000", "SUCCESS");
        } catch (Exception e) {
            writeResponse(resp, "UNKNOWN", "UNKNOWN");
            e.printStackTrace();
        }
        resp.getWriter().flush();
    }


    private void handleSmsRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String address = req.getParameter(ADDRESS);
        String message = req.getParameter(MESSAGE);
        SmsAoRequestMessage smsAoRequestMessage = new SmsAoRequestMessage();
        smsAoRequestMessage.setMessage(message);
        System.out.println("New SMS Received [" + smsAoRequestMessage + "]");
        try {
            validateAndSetAuthentication(req, resp, smsAoRequestMessage);
            validateAndSetAddress(resp, address, smsAoRequestMessage);
            validateAndSetMessage(resp, message, smsAoRequestMessage);
        } catch (Exception e) {
            System.out.println("Received SMS Message Failed : " + smsAoRequestMessage);
            return;
        }

        receivedSms.add(smsAoRequestMessage);
    }

    private void validateAndSetMessage(HttpServletResponse resp, String message, SmsAoRequestMessage smsAoRequestMessage) throws Exception {
        try{
            if ("".equals(message)) {
                throw new Exception();
            }
            smsAoRequestMessage.setMessage(message) ;
        }  catch (Exception e) {
            writeResponse(resp, "400", "Bad Request [ Couldn't find parameter (message) in the request or it is blank ]");
            throw e;
        }

    }

    private void validateAndSetAddress(HttpServletResponse resp, String address, SmsAoRequestMessage smsAoRequestMessage) throws Exception {
        try {
            smsAoRequestMessage.setAddress(address.split(":")[1]);
        } catch (Exception e) {
            writeResponse(resp, "ADDRESS-NOT-SPECIFIED",
                    "Check if the address has been correctly set");
            throw e;
        }
    }

    private void validateAndSetAuthentication(HttpServletRequest req, HttpServletResponse resp, SmsAoRequestMessage smsAoRequestMessage) throws Exception {
        try {
            String[] authentication = getAppIdPassword(req.getHeader(AUTHORIZATION));
            for (String s : authentication) {
                if ("".equals(s)) {
                    throw new Exception();
                }
            }
            smsAoRequestMessage.setAppId(authentication[0]);
            smsAoRequestMessage.setPassword(authentication[1]);
        } catch (Exception e) {
            writeResponse(resp, "UNAUTHORIZED-REQUEST",
                    "Request could not be authenticated, make sure proper authentication parameters are sent");
            throw e;
        }
    }

    private void writeResponse(HttpServletResponse resp, String statusCode, String statusMessage) throws IOException {
        final long correlationId = (long) Math.random() * 1000000000l;
        resp.getWriter().write(MessageFormat.format(RESPONSE_MESSAGE, correlationId, statusCode, statusMessage));
    }

    /**
     * @param basicAuthHeader authorization parameter value of the request
     * @return a string array containing
     *         array[0] = appId
     *         array[1] = password
     */
    public String[] getAppIdPassword(String basicAuthHeader) {

        basicAuthHeader = basicAuthHeader.substring(6); // remove "BASIC "
        String decoded = new String(Base64.decodeBase64(basicAuthHeader.getBytes()));
        return decoded.split(":");

    }

    public static List<SmsAoRequestMessage> getReceivedMessages() {
        return receivedSms;
    }

}
