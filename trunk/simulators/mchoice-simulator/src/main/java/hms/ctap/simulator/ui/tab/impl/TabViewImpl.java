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

        Component mobilePhoneLayout = createMobilePhoneLayout();
        tabUpperLayout.addComponent(mobilePhoneLayout);
        tabUpperLayout.setComponentAlignment(mobilePhoneLayout, Alignment.TOP_LEFT);

        Component inputFieldPanel = createInputPanel();
        tabUpperLayout.addComponent(inputFieldPanel);
        tabUpperLayout.setComponentAlignment(inputFieldPanel, Alignment.TOP_RIGHT);
        tabLayout.addComponent(tabUpperLayout);

        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSpacing(true);
        tableLayout.setWidth("100%");

        tableLayout.addComponent(receivedMessageTable);
        tableLayout.setComponentAlignment(receivedMessageTable, Alignment.MIDDLE_LEFT);

        tableLayout.addComponent(sentMessageTable);
        tableLayout.setComponentAlignment(sentMessageTable, Alignment.MIDDLE_RIGHT);

        final Refresher c = new Refresher();
        c.setRefreshInterval(4000);
        tableLayout.addComponent(c);
        tabLayout.addComponent(tableLayout);
        return tabLayout;
    }


    public Component createMobilePhoneLayout(){

        VerticalLayout mobilePhoneLayout = new VerticalLayout();
        mobilePhoneLayout.setSpacing(true);
        TextField lastReceivedMsg = new TextField("Last Received Message");
        mobilePhoneLayout.addComponent(lastReceivedMsg);
        Component mobilePhone = createMobilePhone();
        mobilePhoneLayout.addComponent(mobilePhone);        
        return mobilePhoneLayout;
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
        TextArea messageTextField = new TextArea();
        messageTextField.setWidth("98px");
        messageTextField.setHeight("128px");
        messageTextField.setStyleName("message-display");
        backgroundLayout.addComponent(messageTextField);
        return backgroundLayout;
    }


}
