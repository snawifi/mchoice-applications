/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui.tab;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import hms.ctap.simulator.ui.table.ReceivedMessageTable;
import hms.ctap.simulator.ui.table.SentMessageTable;
import hms.ctap.simulator.ui.InputPanel;
import hms.ctap.simulator.ui.MainUI;

/**
 * @author hms
 */
public class SmsTab extends Tab {

    MainUI mainUI;
    InputPanel inputPanel;
    SentMessageTable sentMessageTable;
    ReceivedMessageTable receivedMessageTable;

    public SmsTab(MainUI mainUI) {

        this.mainUI = mainUI;
        inputPanel = new InputPanel(this);
        sentMessageTable = new SentMessageTable();
        receivedMessageTable = new ReceivedMessageTable();
    }

    /**
     * @return a Vertical Layout containing the components for SMS tab
     */
    public Component getSMSTabLayout() {

        VerticalLayout tabLayout = new VerticalLayout();
        tabLayout.setMargin(true);
        Component inputFieldPanel = inputPanel.createInputPanel();
        tabLayout.addComponent(inputFieldPanel);
        tabLayout.setComponentAlignment(inputFieldPanel, Alignment.TOP_RIGHT);

        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setSpacing(true);
        tableLayout.addComponent(receivedMessageTable.getReceivedMessageTable());
        tableLayout.addComponent(sentMessageTable.getSentMessageTable());
        tabLayout.addComponent(tableLayout);
        return tabLayout;
    }

    /**
     * @return the table containing sent messages
     */
    public SentMessageTable getSentMessageTable() {
        return sentMessageTable;
    }

    /**
     * @return the table containing received messages
     */
    public ReceivedMessageTable getReceivedMessageTable() {
        return receivedMessageTable;
    }

    /**
     * @return the panel containing input text fields
     */
    public InputPanel getInputPanel() {
        return inputPanel;
    }

}
