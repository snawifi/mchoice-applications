/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui.tab;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import hms.ctap.simulator.ui.table.ReceivedMessageTable;
import hms.ctap.simulator.ui.table.SentMessageTable;
import hms.ctap.simulator.ui.InputPanel;
import hms.ctap.simulator.ussd.UssdMessageReceiver;
import hms.sdp.ussd.impl.UssdAoRequestMessage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author hms
 */
public class UssdTab extends Tab {

    private InputPanel inputPanel;
    private SentMessageTable sentMessageTable;
    private ReceivedMessageTable receivedMessageTable;
    private static ScheduledExecutorService executorService;

    public UssdTab() {
        inputPanel = new InputPanel(this);
        sentMessageTable = new SentMessageTable();
        receivedMessageTable = new ReceivedMessageTable();
        init();
    }

    private void init() {
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(1);
            try {
                executorService.scheduleAtFixedRate(new Runnable() {
                        public void run() {
                            List<UssdAoRequestMessage> receivedMessages = UssdMessageReceiver.getReceivedMessages();
                            for (int i = 0, receivedMessagesSize = receivedMessages.size(); i < receivedMessagesSize; i++) {
                                UssdAoRequestMessage ussdAoRequestMessage = receivedMessages.get(i);
                                receivedMessageTable.addNewRow(i, ussdAoRequestMessage.getAddress(), ussdAoRequestMessage.getMessage());
                            }
                        }
                    }, 4, 4, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return a Vertical Layout containing the contents inside USSD Tab
     */
    public Component getUssdTabLayout() {

        VerticalLayout tabLayout = new VerticalLayout();
        tabLayout.setMargin(true);
        Component inputFieldPanel = inputPanel.createInputPanel();
        tabLayout.addComponent(inputFieldPanel);
        tabLayout.setComponentAlignment(inputFieldPanel, Alignment.TOP_RIGHT);

        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSpacing(true);

        tableLayout.addComponent(receivedMessageTable.getReceivedMessageTable());
        tableLayout.addComponent(sentMessageTable.getSentMessageTable());
        final Refresher c = new Refresher();
        c.setRefreshInterval(4000);
        tableLayout.addComponent(c);
        tabLayout.addComponent(tableLayout);
        return tabLayout;
    }

    /**
     * @return sent messages table
     */
    @Override
    public SentMessageTable getSentMessageTable() {
        return sentMessageTable;
    }

    /**
     * @return received messages table
     */
    @Override
    public ReceivedMessageTable getReceivedMessageTable() {
        return receivedMessageTable;
    }

    /**
     * @return the Input panel inside this tab
     */
    @Override
    public InputPanel getInputPanel() {
        return inputPanel;
    }


}
