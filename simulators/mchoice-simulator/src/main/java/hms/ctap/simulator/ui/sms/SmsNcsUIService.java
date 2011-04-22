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
package hms.ctap.simulator.ui.sms;

import com.vaadin.ui.Table;
import hms.ctap.simulator.ui.services.NcsService;
import hms.ctap.simulator.ui.services.NcsUIService;
import hms.sdp.ussd.impl.UssdAoRequestMessage;

import java.util.Date;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class SmsNcsUIService implements NcsUIService {

    private Table sentMsgTable;
    private int sentRowCount = 1;
    private int receivedRowCount = 1;
    private Table receivedMsgTable;

    @Override
    public void init() {
        sentMsgTable = new Table("Sent Messages");
        receivedMsgTable = new Table("Received Messages");
    }


    @Override
    public Table createSentMessageService() {

        String[] headings = {"Time", "Phone No", "Message", "Status"};
        for (String heading : headings) {
            sentMsgTable.addContainerProperty(heading, String.class, null);
        }

        return sentMsgTable;
    }

    @Override
    public Table createReceivedMessageService() {

        String[] headings = {"Time", "Phone No", "Message", "Status"};
        for (String heading : headings) {
            receivedMsgTable.addContainerProperty(heading, String.class, null);
        }

        return receivedMsgTable;
    }

    @Override
    public void addElementToReceiveTable(int objectId, Object object, String status) {

        if (receivedMsgTable.getItem(objectId) == null) {
            UssdAoRequestMessage ussdAoRequestMessage = (UssdAoRequestMessage) object;
            receivedMsgTable.addItem(new Object[]{new Date(), ussdAoRequestMessage.getAddress(),
                    ussdAoRequestMessage.getMessage(), status}, receivedRowCount);
            receivedRowCount++;
        }
    }

    @Override
    public void addElementToSentTable(String date, String address, String message, String status) {

        sentMsgTable.addItem(new Object[]{date, address, message, status}, sentRowCount);
        sentRowCount++;
    }

    @Override
    public NcsService getNcsService() {
        return NcsService.SMS;
    }
}
