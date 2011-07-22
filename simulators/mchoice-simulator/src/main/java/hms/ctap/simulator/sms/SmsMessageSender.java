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


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class SmsMessageSender {
    private static SmsMessageSender smsMessageSender = new SmsMessageSender();
    private HttpClient httpClient;

    /*this number will be used for access the smpp-server*/
    private String url = "http://127.0.0.1:10088/inject_mo";

    private SmsMessageSender() {
        httpClient = new HttpClient();
    }

    /**
     * changed the method to make SMPPSim compatible
     * the post method was removed and a get method ws added to have a compatibility.
     */
    public void sendMessage (String myNumber, String address, String message) throws IOException {

        /*create get method instead of post method. */
        final GetMethod getMethod = new GetMethod(url);

        /*append headers for sending */
        getMethod.addRequestHeader("CONTENT_TYPE", "");
        getMethod.addRequestHeader("ACCEPT", "text/xml");

        /*create query strings for the request*/
        NameValuePair[] nameValuePairs = new NameValuePair[]{

                /*append message to the query string*/
                new NameValuePair("short_message", message),

                /*append the encrypted 4n number */
                new NameValuePair("source_addr", myNumber),

                /*other message details*/
                new NameValuePair("destination_addr",address),
                new NameValuePair("service_type",""),
                new NameValuePair("submit", "Submit Message"),
                new NameValuePair("source_addr_ton", "1"),
                new NameValuePair("source_addr_npi", "1"),
                new NameValuePair("dest_addr_ton", "1"),
                new NameValuePair("dest_addr_npi", "1"),
                new NameValuePair("esm_class", "0"),
                new NameValuePair("protocol_ID",""),
                new NameValuePair("priority_flag",""),
                new NameValuePair("registered_delivery_flag", "0"),
                new NameValuePair("data_coding", "0"),
                new NameValuePair("user_message_reference",""),
                new NameValuePair("source_port",""),
                new NameValuePair("destination_port",""),
                new NameValuePair("sar_msg_ref_num",""),
                new NameValuePair("sar_total_segments",""),
                new NameValuePair("user_response_code",""),
                new NameValuePair("privacy_indicator",""),
                new NameValuePair("payload_type",""),
                new NameValuePair("message_payload",""),
                new NameValuePair("callback_num",""),
                new NameValuePair("source_subaddress",""),
                new NameValuePair("dest_subaddress",""),
                new NameValuePair("language_indicator",""),
                new NameValuePair("tlv1_tag",""),
                new NameValuePair("tlv1_len",""),
                new NameValuePair("tlv1_val",""),
                new NameValuePair("tlv2_tag",""),
                new NameValuePair("tlv2_len",""),
                new NameValuePair("tlv2_val",""),
                new NameValuePair("tlv3_tag",""),
                new NameValuePair("tlv3_len",""),
                new NameValuePair("tlv3_val",""),
                new NameValuePair("tlv4_tag",""),
                new NameValuePair("tlv4_len",""),
                new NameValuePair("tlv4_val",""),
                new NameValuePair("tlv5_tag",""),
                new NameValuePair("tlv5_len",""),
                new NameValuePair("tlv5_val",""),
                new NameValuePair("tlv6_tag",""),
                new NameValuePair("tlv6_len",""),
                new NameValuePair("tlv6_val",""),
                new NameValuePair("tlv7_tag",""),
                new NameValuePair("tlv7_len",""),
                new NameValuePair("tlv7_val","")

        };

        /*set the query strings*/
        getMethod.setQueryString(nameValuePairs);

        //print string about the details
        System.out.println(new StringBuilder()
                .append("Sending SMS to application [ address: ").append(address).append(",query: ").append(getMethod.getQueryString())
                .append(" ,message: ").append(message).append(" ]").toString());

        /*request the get method*/
        httpClient.executeMethod(getMethod);

    }

    public static SmsMessageSender getInstance() {

        if (smsMessageSender == null) {
            smsMessageSender = new SmsMessageSender();
        }
        return smsMessageSender;
    }
}
