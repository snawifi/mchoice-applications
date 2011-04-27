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
import java.util.ArrayList;
import java.util.List;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class SmsMessageReceiver extends HttpServlet {

    private static final List<SmsAoRequestMessage> receivedSms = new ArrayList<SmsAoRequestMessage>();

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String authorization = req.getHeader("Authorization");
            String address = req.getParameter("address");
            String message = req.getParameter("message");
            String authetication[] = getAppIdPassword(authorization);

            SmsAoRequestMessage smsAoRequestMessage = new SmsAoRequestMessage();
            smsAoRequestMessage.setAddress(address);
            smsAoRequestMessage.setMessage(message);
            smsAoRequestMessage.setAppId(authetication[0]);
            smsAoRequestMessage.setPassword(authetication[1]);
            System.out.println("New SMS Received [" + smsAoRequestMessage + "]");
            receivedSms.add(smsAoRequestMessage);

            resp.getWriter().write("<mchoice_sdp_sms_response>\n" +
                    "   <version>1.0</version>\n" +
                    "   <correlator>10051016580002</correlator>\n" +
                    "   <status_code>SBL-SMS-MT-2000</status_code>\n" +
                    "   <status_message>SUCCESS</status_message>\n" +
                    "</mchoice_sdp_sms_response>");
            resp.getWriter().flush();
        }

        /**
         *
         * @param basicAuthHeader authorization parameter value of the request
         * @return a string array containing
         *              array[0] = appId
         *              array[1] = password
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
