/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import hms.ctap.simulator.ui.tab.TabView;

/**
 *
 * @author hms
 */
public class TabSheetPanel {

    private TabView smsTabView;
    private TabView ussdTabView;

    public TabSheetPanel(TabView smsTabView, TabView ussdTabView) {
        this.smsTabView = smsTabView;
        this.ussdTabView = ussdTabView;
    }

    /**
     * 
     * @return the tabView sheet
     */
    public Component createTabSheetPanel(){

        TabSheet tabSheet = new TabSheet();
//        tabSheet.addTab(smsTabView.getTabLayout(), "SMS", new ThemeResource("images/mobile_phone2.ico"));
        tabSheet.addTab(ussdTabView.getTabLayout(), "USSD", new ThemeResource("images/mobile_phone2.ico"));      
        return tabSheet;
    }

}
