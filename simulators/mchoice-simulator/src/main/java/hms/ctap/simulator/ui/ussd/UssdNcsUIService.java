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
package hms.ctap.simulator.ui.ussd;

import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import hms.ctap.simulator.ui.services.NcsService;
import hms.ctap.simulator.ui.services.NcsUIService;
import hms.sdp.ussd.impl.UssdAoRequestMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class UssdNcsUIService implements NcsUIService {

    private Table sentMsgTable;
    private int sentRowCount = 1;
    private Table receivedMsgTable;

    //@Override
    public void init() {
        sentMsgTable = new Table("Sent Messages");
        receivedMsgTable = new Table("Received Messages");
    }


    //@Override
    public Table createSentMessageService() {

        String[] headings = {"Time", "Phone #", "Message", "Status"};
        for (String heading : headings) {
            sentMsgTable.addContainerProperty(heading, String.class, null);
        }
        sentMsgTable.setColumnExpandRatio(headings[0], 0.08f);
        sentMsgTable.setColumnExpandRatio(headings[1], 0.25f);
        sentMsgTable.setColumnExpandRatio(headings[2], 0.60f);
        sentMsgTable.setColumnExpandRatio(headings[3], 0.07f);

        sentMsgTable.setHeight("100%");
        sentMsgTable.setWidth("100%");
        return sentMsgTable;

    }

    //@Override
    public Table createReceivedMessageService() {

        String[] headings = {"Time", "Phone #", "Conversation ID", "Message"};
        for (String heading : headings) {
            receivedMsgTable.addContainerProperty(heading, String.class, null);
        }
        receivedMsgTable.setColumnExpandRatio(headings[0], 0.08f);
        receivedMsgTable.setColumnExpandRatio(headings[1], 0.20f);
        receivedMsgTable.setColumnExpandRatio(headings[2], 0.17f);
        receivedMsgTable.setColumnExpandRatio(headings[3], 0.50f);

        receivedMsgTable.setHeight("100%");
        receivedMsgTable.setWidth("100%");
        return receivedMsgTable;
    }


    /**
     * @param objectId
     * @param object
     */
    //@Override
    public void addElementToReceiveTable(int objectId, Object object) {

        if (receivedMsgTable.getItem(objectId) == null) {
            UssdAoRequestMessage ussdAoRequestMessage = (UssdAoRequestMessage) object;
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            receivedMsgTable.addItem(new Object[]{dateFormat.format(new Date()), ussdAoRequestMessage.getAddress(),
                    ussdAoRequestMessage.getConversationId(), ussdAoRequestMessage.getMessage()}, objectId);
        }
    }

    /**
     * @param date    Date
     * @param address
     * @param message
     * @param status
     */
    //@Override
    public void addElementToSentTable(String date, String address, String message, String status) {

        sentMsgTable.addItem(new Object[]{date, address, message, status}, sentRowCount);
        sentRowCount++;
    }

    /**
     * this method creates a button to clear the recieved messsages table
     * @return
     */
    public Button createClearReceivedMessagesButton() {

        Button clearButton = new Button("Clear Received Messages", new Button.ClickListener(){

            public void buttonClick(com.vaadin.ui.Button.ClickEvent clickEvent) {
                receivedMsgTable.removeAllItems();
            }
        });
        return clearButton;
    }

    /**
     * this method creates a button to clear the sent messages table
     * meanwhile it sets the row number variable to 1
     * @return
     */
    public Button createClearSentMessagesButton() {

        Button clearButton = new Button("Clear Sent Messages", new Button.ClickListener(){

            public void buttonClick(Button.ClickEvent clickEvent) {
                sentMsgTable.removeAllItems();
                sentRowCount = 1;
            }
        });
        return clearButton;
    }

    //@Override
    public NcsService getNcsService() {
        return NcsService.USSD;
    }
}
