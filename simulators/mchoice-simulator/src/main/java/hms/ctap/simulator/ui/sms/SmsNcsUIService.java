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
        sentMsgTable.addContainerProperty("Time", String.class, null);
        sentMsgTable.addContainerProperty("Phone No", String.class, null);
        sentMsgTable.addContainerProperty("Message", String.class, null);
        sentMsgTable.addContainerProperty("Status", String.class, null);
        sentMsgTable.setWidth("450px");
        return sentMsgTable;
    }

    @Override
    public Table createReceivedMessageService() {
        receivedMsgTable.addContainerProperty("Time", String.class, null);
        receivedMsgTable.addContainerProperty("Phone No", String.class, null);
        receivedMsgTable.addContainerProperty("Message", String.class, null);
        receivedMsgTable.addContainerProperty("Status", String.class, null);
        receivedMsgTable.setWidth("440px");
        return receivedMsgTable;
    }


    /**
     * @param objectId
     * @param date    Date
     * @param phoneNo Phone Number
     * @param message Received Message
     * @param status  status of the sent message
     */
    @Override
    public void addElementToReceiveTable(int objectId, Date date, String phoneNo, String message, String status) {

        receivedMsgTable.addItem(new Object[]{date, phoneNo, message, status}, receivedRowCount);
        receivedRowCount++;
    }

    /**
     * @param date    Date
     * @param phoneNo Phone Number
     * @param message Received Message
     * @param status  status of the sent message
     */
    @Override
    public void addElementToSentTable(Date date, String phoneNo, String message, String status) {

        sentMsgTable.addItem(new Object[]{date, phoneNo, message, status}, sentRowCount);
        sentRowCount++;
    }

    @Override
    public NcsService getNcsService() {
        return NcsService.USSD;
    }
}
