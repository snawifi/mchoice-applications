/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui.tab.impl;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.ui.*;
import hms.ctap.simulator.ui.services.NcsUIService;
import hms.ctap.simulator.ui.tab.TabView;
import hms.ctap.simulator.ussd.UssdMessageReceiver;
import hms.ctap.simulator.ussd.UssdMessageSender;
import hms.sdp.ussd.impl.UssdAoRequestMessage;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author hms
 */
public class TabViewImpl extends TabView {

    private Table sentMessageTable;
    private Table receivedMessageTable;
    private static ScheduledExecutorService executorService;
    private NcsUIService ncsUIService;


    public TabViewImpl(NcsUIService ncsUIService) {
        init();
        this.ncsUIService = ncsUIService;
        sentMessageTable = ncsUIService.createSentMessageService();
        receivedMessageTable = ncsUIService.createReceivedMessageService();
    }

    public void init() {
        super.init();
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(1);
            try {
                executorService.scheduleAtFixedRate(new Runnable() {
                        public void run() {
                            List<UssdAoRequestMessage> receivedMessages = UssdMessageReceiver.getReceivedMessages();
                            for (int i = 0, receivedMessagesSize = receivedMessages.size(); i < receivedMessagesSize; i++) {
                                UssdAoRequestMessage ussdAoRequestMessage = receivedMessages.get(i);
                                ncsUIService.addElementToReceiveTable(i, new Date(), ussdAoRequestMessage.getAddress(),
                                        ussdAoRequestMessage.getMessage(), "Success");
                            }
                        }
                    }, 4, 4, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Button createSendMsgButton()  {

        Button sendMsgButton = new Button("Send");
        sendMsgButton.addListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                final UssdMessageSender instance = UssdMessageSender.getInstance();
                final String address = getPhoneNoField().getValue().toString();
                final String message = getMessageField().getValue().toString();
                try {
                    final String url = getUrlTextField().getValue().toString();
                    instance.sendMessage(url, address,
                            message);
                    ncsUIService.addElementToSentTable(new Date(), address, message, "SENT");
                } catch (Exception e) {
                    ncsUIService.addElementToSentTable(new Date(), address, message, "FAILED");
                    e.printStackTrace();
                }
            }
        });
        return sendMsgButton;
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

        HorizontalLayout leftTableLayout =  new HorizontalLayout();
        leftTableLayout.setWidth("100%");
        leftTableLayout.addComponent(receivedMessageTable);
        leftTableLayout.setComponentAlignment(receivedMessageTable, Alignment.MIDDLE_CENTER);

        tableLayout.addComponent(leftTableLayout);
        tableLayout.setComponentAlignment(leftTableLayout, Alignment.MIDDLE_LEFT);

        HorizontalLayout rightTableLayout =  new HorizontalLayout();
        rightTableLayout.setWidth("100%");
        rightTableLayout.addComponent(sentMessageTable);
        rightTableLayout.setComponentAlignment(sentMessageTable, Alignment.MIDDLE_CENTER);

        tableLayout.addComponent(rightTableLayout);
        tableLayout.setComponentAlignment(rightTableLayout, Alignment.MIDDLE_RIGHT);

        final Refresher c = new Refresher();
        c.setRefreshInterval(4000);
        tableLayout.addComponent(c);
        tabLayout.addComponent(tableLayout);
        return tabLayout;
    }


    
    /**
     *
     * @return a vertical layout containing mobile phone image
     */
    public Component createMobilePhone(){

        VerticalLayout backgroundLayout = new VerticalLayout();
        backgroundLayout.setWidth("119px");
        backgroundLayout.setHeight("236px");        
        backgroundLayout.setStyleName("mobile-phone-background");

        VerticalLayout displayLayout = new VerticalLayout();
        Label phoneNoLabel = new Label();
        phoneNoLabel.setWidth("98px");
        phoneNoLabel.setStyleName("address-display");
        displayLayout.addComponent(phoneNoLabel);
        Label messageTextField = new Label();
        messageTextField.setWidth("98px");
        messageTextField.setStyleName("message-display");
        displayLayout.addComponent(messageTextField);

        backgroundLayout.addComponent(displayLayout);
        return backgroundLayout;
    }




}
