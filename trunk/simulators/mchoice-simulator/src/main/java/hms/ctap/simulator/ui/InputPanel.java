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
package hms.ctap.simulator.ui;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import hms.ctap.simulator.ui.table.SentMessageTable;
import hms.ctap.simulator.ui.tab.Tab;
import hms.ctap.simulator.ussd.UssdMessageSender;

import java.util.Date;

/**
 * @author hms
 */
public class InputPanel {

    private Tab parentTab;
    private TextField urlTextField;
    private TextField phoneNoField;
    private TextArea messageField;

    public InputPanel(Tab parentTab) {
        this.parentTab = parentTab;
        urlTextField = new TextField();
        urlTextField.setWidth("150px");
        phoneNoField = new TextField();
        phoneNoField.setWidth("150px");
        messageField = new TextArea();
        messageField.setWidth("150px");
    }


    /**
     * @return the Panel containing input text fields
     */
    public Component createInputPanel() {

        Panel inputPanel = new Panel("Send Message");
//        inputPanel.setWidth("275px");

        VerticalLayout vertical = new VerticalLayout();
        vertical.setSpacing(true);

        HorizontalLayout urlLayout = createUrlLayout();
        HorizontalLayout phoneLayout = createPhoneLayout();
        HorizontalLayout msgLayout = createMessageLayout();
        vertical.addComponent(urlLayout);
        vertical.addComponent(phoneLayout);
        vertical.addComponent(msgLayout);

        Button sendButton = createSendMsgButton(this.getParentTab().getSentMessageTable());
        vertical.addComponent(sendButton);
        vertical.setComponentAlignment(sendButton, Alignment.BOTTOM_RIGHT);

        inputPanel.addComponent(vertical);
        return inputPanel;
    }

    private HorizontalLayout createMessageLayout() {
        HorizontalLayout msgLayout = new HorizontalLayout();
        msgLayout.setSpacing(true);
        Label msgLabel = new Label("Message ");
        msgLabel.setWidth("75px");
        msgLayout.addComponent(msgLabel);
        msgLayout.addComponent(messageField);
        messageField.setValue("Test Message");
        return msgLayout;
    }

    private HorizontalLayout createPhoneLayout() {
        HorizontalLayout phoneLayout = new HorizontalLayout();
        phoneLayout.setSpacing(true);
        Label phoneNoLabel = new Label("Phone #");
        phoneNoLabel.setWidth("75px");
        phoneLayout.addComponent(phoneNoLabel);
        phoneLayout.addComponent(phoneNoField);
        phoneNoField.setValue("947212345678");
        return phoneLayout;
    }

    private HorizontalLayout createUrlLayout() {
        HorizontalLayout urlLayout = new HorizontalLayout();
        urlLayout.setSpacing(true);
        Label urlLabel = new Label("App URL");
        urlLabel.setWidth("75px");
        urlLayout.addComponent(urlLabel);
        urlLayout.addComponent(urlTextField);
        urlTextField.setValue("http://127.0.0.1:8080/");
        return urlLayout;
    }

    /**
     * @return the phone number entered into the text field
     */
    public String getPhoneNo() {
        return phoneNoField.getValue().toString();
    }

    /**
     * @return the message entered into the text field
     */
    public String getMessage() {
        return messageField.getValue().toString();
    }

    /**
     * @param sentMsgTable
     * @return the Send Message Button
     */
    private Button createSendMsgButton(final SentMessageTable sentMsgTable) {

        Button sendMsgButton = new Button("Send");
        sendMsgButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                final UssdMessageSender instance = UssdMessageSender.getInstance();
                try {
                    instance.sendMessage(urlTextField.getValue().toString(), phoneNoField.getValue().toString(),
                            messageField.getValue().toString());
                    sentMsgTable.addNewRow(new Date(), getPhoneNo(), getMessage(), "SENT");
                } catch (Exception e) {
                    sentMsgTable.addNewRow(new Date(), getPhoneNo(), getMessage(), "FAILED");
                    e.printStackTrace();
                }
            }
        });
        return sendMsgButton;
    }

    public Tab getParentTab() {
        return parentTab;
    }

    public void setParentTab(Tab parentTab) {
        this.parentTab = parentTab;
    }


}
