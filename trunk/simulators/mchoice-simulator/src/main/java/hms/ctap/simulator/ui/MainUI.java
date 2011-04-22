/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui;


import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
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
        rootLayout.setStyleName("root-layout");
        Panel mainPanel = new Panel();
        mainPanel.addComponent(createHeader());
        Component tabSheet = createMainUI().createTabSheetPanel();
        mainPanel.addComponent(tabSheet);
        rootLayout.addComponent(mainPanel);
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
        header.setWidth("100%");
        Embedded logo = new Embedded("", new ThemeResource("images/hms_logo.jpg"));
        header.addComponent(logo);
        header.setComponentAlignment(logo, Alignment.TOP_LEFT);
        Label headerLabel = new Label("mChoice Simulator");
        headerLabel.setStyleName("simulator-header");
        header.addComponent(headerLabel);
        header.setComponentAlignment(headerLabel, Alignment.MIDDLE_RIGHT);
        return header;
    }

}
