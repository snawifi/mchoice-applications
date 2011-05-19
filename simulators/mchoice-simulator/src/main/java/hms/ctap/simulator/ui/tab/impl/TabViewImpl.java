/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui.tab.impl;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.ui.*;
import hms.ctap.simulator.ui.services.NcsService;
import hms.ctap.simulator.ui.services.NcsUIService;
import hms.ctap.simulator.ui.tab.TabView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author hms
 */
public class TabViewImpl extends TabView {

    private static final int REFRESH_INTERVAL = 4000;

    private ScheduledExecutorService executorService;

    final private Table sentMessageTable;
    final private Table receivedMessageTable;
    final private NcsUIService ncsUIService;
    final private Label phoneImageNumLabel;
    final private Label phoneImageMessageLabel;
    private Refresher refresher;


    public TabViewImpl(NcsUIService ncsUIService) {
        init();
        this.ncsUIService = ncsUIService;
        sentMessageTable = ncsUIService.createSentMessageService();
        receivedMessageTable = ncsUIService.createReceivedMessageService();

        phoneImageNumLabel = new Label();
        phoneImageMessageLabel = new Label();
        phoneImageNumLabel.setWidth("98px");
        phoneImageNumLabel.setStyleName("address-display");
        phoneImageMessageLabel.setContentMode(Label.CONTENT_PREFORMATTED);
        phoneImageMessageLabel.setWidth("98px");
        phoneImageMessageLabel.setStyleName("message-display");
        refresher = new Refresher();
    }

    public void init() {
        super.init();
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    try {
                        final NcsService ncsService = ncsUIService.getNcsService();
                        List receivedMessages = ncsService.receivedMessages();
                        for (int i = 0, receivedMessagesSize = receivedMessages.size(); i < receivedMessagesSize; i++) {
                            ncsUIService.addElementToReceiveTable(i, receivedMessages.get(i));
                        }
                        if (receivedMessages.size() > 0) {
                            ncsService.updatePhoneView(phoneImageNumLabel, phoneImageMessageLabel, receivedMessages.get(receivedMessages.size() - 1));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 4, 4, TimeUnit.SECONDS);
        }
    }

    @Override
    public Button createSendMsgButton() {

        Button sendMsgButton = new Button("Send");
        sendMsgButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                final String address = encryptAddress(getPhoneNoField().getValue().toString());
                final String message = getMessageField().getValue().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                try {
                    final String url = getUrlTextField().getValue().toString();
                    ncsUIService.getNcsService().sendMessage(url, address, message);
                    ncsUIService.addElementToSentTable(dateFormat.format(new Date()), address, message, "Success");
                } catch (Exception e) {
                    ncsUIService.addElementToSentTable(dateFormat.format(new Date()), address, message, "Failed");
                    e.printStackTrace();
                }
            }
        });
        return sendMsgButton;
    }

    /**
     *
     * @param phoneNo
     * @return  the MD5 checksum of the phoneNo
     */
    private String encryptAddress(String phoneNo) {
        String encryptedAddress = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(phoneNo.getBytes());
            for (int i=0; i < bytes.length; i++) { //converting byte array to hex string
                encryptedAddress += Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedAddress;
    }

    @Override
    public Component getTabLayout() {

        VerticalLayout tabLayout = new VerticalLayout();
        tabLayout.setMargin(true);

        HorizontalLayout tabUpperLayout = new HorizontalLayout();
        tabUpperLayout.setWidth("100%");
        tabUpperLayout.setMargin(true);

        Component mobilePhoneLayout = createMobilePhone();
        tabUpperLayout.addComponent(mobilePhoneLayout);
        tabUpperLayout.setComponentAlignment(mobilePhoneLayout, Alignment.BOTTOM_LEFT);

        Component inputFieldPanel = createInputPanel();
        tabUpperLayout.addComponent(inputFieldPanel);
        tabUpperLayout.setComponentAlignment(inputFieldPanel, Alignment.TOP_RIGHT);
        tabLayout.addComponent(tabUpperLayout);

        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSpacing(true);
        tableLayout.setWidth("100%");

        HorizontalLayout receivedMessageTableLayout = new HorizontalLayout();
        receivedMessageTableLayout.setStyleName("received-message-table");
        receivedMessageTableLayout.addComponent(receivedMessageTable);

        HorizontalLayout sentMessageTableLayout = new HorizontalLayout();
        sentMessageTableLayout.setStyleName("sent-message-table");
        sentMessageTableLayout.addComponent(sentMessageTable);

        tableLayout.addComponent(receivedMessageTableLayout);
        tableLayout.addComponent(sentMessageTableLayout);
        tableLayout.setComponentAlignment(receivedMessageTableLayout, Alignment.MIDDLE_CENTER);
        tableLayout.setComponentAlignment(sentMessageTableLayout, Alignment.MIDDLE_CENTER);

        refresher.setRefreshInterval(REFRESH_INTERVAL);
        tableLayout.addComponent(refresher);
        tabLayout.addComponent(tableLayout);
        return tabLayout;
    }


    /**
     * @return a vertical layout containing mobile phone image
     */
    public Component createMobilePhone() {

        VerticalLayout backgroundLayout = new VerticalLayout();
        backgroundLayout.setWidth("119px");
        backgroundLayout.setHeight("236px");
        backgroundLayout.setStyleName("mobile-phone-background");

        VerticalLayout displayLayout = new VerticalLayout();
        displayLayout.setWidth("98px");
        displayLayout.addComponent(phoneImageNumLabel);
        displayLayout.addComponent(phoneImageMessageLabel);
        displayLayout.addComponent(refresher);
        displayLayout.setExpandRatio(phoneImageMessageLabel, 1.0f);

        backgroundLayout.addComponent(displayLayout);
        backgroundLayout.setExpandRatio(displayLayout, 1.0f);
        return backgroundLayout;
    }


}
