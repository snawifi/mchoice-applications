/*
 *   (C) Copyright 2010-2011 hSenid Software International (Pvt) Limited.
 *   All Rights Reserved.
 *
 *   These materials are unpublished, proprietary, confidential source code of
 *   hSenid Software International (Pvt) Limited and constitute a TRADE SECRET
 *   of hSenid Software International (Pvt) Limited.
 *
 *   hSenid Software International (Pvt) Limited retains all title to and intellectual
 *   property rights in these materials.
 *
 */
package hms.ctap.simulator;

import com.vaadin.Application;
import com.vaadin.ui.Window;
import hms.ctap.simulator.ui.MainUI;

public class SimulatorApplication extends Application {

    public void init() {

        Window mainWindow = new Window("Simulator");             
        mainWindow.setTheme("runo");       
        mainWindow.addComponent(new MainUI().getRootLayout());
        setMainWindow(mainWindow);
    }



}
