/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author hms
 */
public class MainUI {

    TabSheetPanel tabSheetPanel;

    public MainUI() {
        tabSheetPanel = new TabSheetPanel(this);        
    }

    /**
     *
     * @return the root layout of the main UI
     */
    public Component getRootLayout(){

        VerticalLayout rootLayout = new VerticalLayout();         
        rootLayout.addComponent(createHeader());
        Component tabPanel = tabSheetPanel.createTabSheetPanel();
        rootLayout.addComponent(tabPanel);
        return rootLayout;
    }    

    /**
     *
     * @return the TabSheetPanel inside main UI
     */
    public TabSheetPanel getTabSheetPanel() {
        return tabSheetPanel;
    }

    /**
     *
     * @param tabSheetPanel TabSheetPanel to be set inside the Main UI
     */
    public void setTabSheetPanel(TabSheetPanel tabSheetPanel) {
        this.tabSheetPanel = tabSheetPanel;
    }

    private Component createHeader() {        
        
        HorizontalLayout header = new HorizontalLayout();
//        header.setWidth("100%");
//        Embedded logo = new Embedded("", new ThemeResource("hms_logo.jpg"));
//        header.addComponent(logo);
//        header.setComponentAlignment(logo, Alignment.TOP_LEFT);
//        Label headerLabel = new Label("Simulator");
//        headerLabel.setStyleName("simulator-header");
//        header.addComponent(headerLabel);
//        header.setComponentAlignment(headerLabel, Alignment.MIDDLE_RIGHT);
        return header;
    }

}
