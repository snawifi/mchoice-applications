/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui;


import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
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
        mainPanel.setWidth("800px");
        mainPanel.addComponent(createHeader());
        Component tabSheet = createMainUI().createTabSheetPanel();
        mainPanel.addComponent(tabSheet);

        rootLayout.addComponent(mainPanel);
        rootLayout.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
        return rootLayout;
    }

    private TabSheetPanel createMainUI() {

//        final SmsNcsUIService smsNcsUIService = new SmsNcsUIService();
//        smsNcsUIService.init();
        final UssdNcsUIService ussdNcsUIService = new UssdNcsUIService();
        ussdNcsUIService.init();

//        final TabViewImpl smsTabView = new TabViewImpl(smsNcsUIService);
        final TabViewImpl ussdTabView = new TabViewImpl(ussdNcsUIService);
//        smsTabView.init();
        ussdTabView.init();

        return new TabSheetPanel(null, ussdTabView);
    }

    private AbstractLayout createHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setSizeFull();
        Embedded logo = new Embedded("", new ThemeResource("images/hms_logo.jpg"));
        header.addComponent(logo);
        logo.setStyleName("hsenid-logo");
        header.setComponentAlignment(logo, Alignment.TOP_LEFT);
        Label headerLabel = new Label("mChoice Simulator");
        headerLabel.setStyleName("simulator-header");
        header.addComponent(headerLabel);
        header.setComponentAlignment(headerLabel, Alignment.MIDDLE_RIGHT);
        return header;
    }

}
