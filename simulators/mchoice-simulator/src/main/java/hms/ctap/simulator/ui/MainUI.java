/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import hms.ctap.simulator.ui.sms.SmsNcsUIService;
import hms.ctap.simulator.ui.tab.impl.TabViewImpl;
import hms.ctap.simulator.ui.ussd.UssdNcsUIService;

/**
 * @author hms
 */
public class MainUI {

    /**
     * @return the root layout of the main UI
     */
    public Component getRootLayout() {

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponent(createHeader());

        Component tabPanel = createMainUI().createTabSheetPanel();
        rootLayout.addComponent(tabPanel);
        return rootLayout;
    }

    private TabSheetPanel createMainUI() {

        final SmsNcsUIService smsNcsUIService = new SmsNcsUIService();
        smsNcsUIService.init();
        final UssdNcsUIService ussdNcsUIService = new UssdNcsUIService();
        ussdNcsUIService.init();

        final TabViewImpl smsTabView = new TabViewImpl(smsNcsUIService);
        final TabViewImpl ussdTabView = new TabViewImpl(ussdNcsUIService);
        smsTabView.init();
        ussdTabView.init();


        return new TabSheetPanel(smsTabView, ussdTabView);
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
