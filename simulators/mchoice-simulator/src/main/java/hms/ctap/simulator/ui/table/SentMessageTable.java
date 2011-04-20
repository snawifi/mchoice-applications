/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui.table;


import com.vaadin.ui.Table;
import java.util.Date;

/**
 *
 * @author hms
 */
public class SentMessageTable {

    private Table sentMsgTable;
    private int rowCount;

    /**
     *
     * @return the table containing sent messages
     */
    public Table getSentMessageTable(){

        sentMsgTable = new Table("Sent Messages");
        rowCount = 1;
        sentMsgTable.addContainerProperty("Time", String.class, null);
        sentMsgTable.addContainerProperty("Phone No", String.class, null);
        sentMsgTable.addContainerProperty("Message", String.class, null);
        sentMsgTable.addContainerProperty("Status", String.class, null);
        sentMsgTable.setWidth("450px");
        return sentMsgTable;

    }

    /**
     *
     * @param date Date
     * @param phoneNo Phone Number
     * @param message Received Message
     * @param status status of the sent message
     */
    public void addNewRow(Date date, String phoneNo, String message, String status){

        sentMsgTable.addItem(new Object[]{date, phoneNo, message, status}, rowCount);
        rowCount++;
    }

}
