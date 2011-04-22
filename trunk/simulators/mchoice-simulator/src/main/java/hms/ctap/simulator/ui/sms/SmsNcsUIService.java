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

        String[] headings = {"Time", "Phone No", "Message", "Status"};
        sentMsgTable.addContainerProperty(headings[0], String.class, null);
        sentMsgTable.addContainerProperty(headings[1], String.class, null);
        sentMsgTable.addContainerProperty(headings[2], String.class, null);
        sentMsgTable.addContainerProperty(headings[3], String.class, null);

        sentMsgTable.setStyleName("message-table-caption");
        sentMsgTable.setSizeFull();
        sentMsgTable.setColumnExpandRatio(headings[0], 0.15f);
        sentMsgTable.setColumnExpandRatio(headings[1], 0.2f);
        sentMsgTable.setColumnExpandRatio(headings[2], 0.55f);
        sentMsgTable.setColumnExpandRatio(headings[3], 0.1f);
        return sentMsgTable;
    }

    @Override
    public Table createReceivedMessageService() {

        String[] headings = {"Time", "Phone No", "Message", "Status"};
        receivedMsgTable.addContainerProperty(headings[0], String.class, null);
        receivedMsgTable.addContainerProperty(headings[1], String.class, null);
        receivedMsgTable.addContainerProperty(headings[2], String.class, null);
        receivedMsgTable.addContainerProperty(headings[3], String.class, null);

        receivedMsgTable.setStyleName("message-table-caption");
        receivedMsgTable.setSizeFull();
        receivedMsgTable.setColumnExpandRatio(headings[0], 0.15f);
        receivedMsgTable.setColumnExpandRatio(headings[1], 0.2f);
        receivedMsgTable.setColumnExpandRatio(headings[2], 0.55f);
        receivedMsgTable.setColumnExpandRatio(headings[3], 0.1f);
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
