/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui;

import com.vaadin.terminal.ThemeResource;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import hms.ctap.simulator.ui.tab.SmsTab;
import hms.ctap.simulator.ui.tab.UssdTab;

/**
 *
 * @author hms
 */
public class TabSheetPanel {

    MainUI mainUI;
    SmsTab smsTab;
    UssdTab ussdTab;

    public TabSheetPanel(MainUI mainUI) {
        this.mainUI = mainUI;
        smsTab = new SmsTab(mainUI);
        ussdTab = new UssdTab();
    }


    /**
     * 
     * @return the tab sheet
     */
    public Component createTabSheetPanel(){

        Panel tabSheetPanel = new Panel();
        TabSheet tabSheet = new TabSheet();        
        tabSheet.addTab(smsTab.getSMSTabLayout(), "SMS", new ThemeResource("mobile_phone2.ico"));
        tabSheet.addTab(ussdTab.getUssdTabLayout(), "USSD", new ThemeResource("mobile_phone2.ico"));
        tabSheetPanel.addComponent(tabSheet);
        return tabSheetPanel;
    }

    /**
     *
     * @return the SmsTab
     */
    public SmsTab getSmsTab() {
        return smsTab;
    }

    /**
     *
     * @return the UssdTab
     */
    public UssdTab getUssdTab(){
        return ussdTab;
    }
    

}
