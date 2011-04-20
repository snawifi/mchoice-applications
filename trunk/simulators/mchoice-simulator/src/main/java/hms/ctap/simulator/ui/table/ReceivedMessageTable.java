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
public class ReceivedMessageTable {

    private Table receivedMsgTable;


    /**
     *
     * @return the table containing received messages
     */
    public Table getReceivedMessageTable() {

        receivedMsgTable = new Table("Received Messages");
        receivedMsgTable.addContainerProperty("Time", String.class, null);
        receivedMsgTable.addContainerProperty("Phone No", String.class, null);
        receivedMsgTable.addContainerProperty("Message", String.class, null);        
        receivedMsgTable.setWidth("440px");
        return receivedMsgTable;
        
    }

    /**
     *
     * @param itemId
     * @param phoneNo Phone Number
     * @param message Received Message
     */
    public void addNewRow(Object itemId, String phoneNo, String message){
        if (receivedMsgTable.getItem(itemId) == null) {
            receivedMsgTable.addItem(new Object[]{new Date(), phoneNo, message}, itemId);
        }
    }

}
