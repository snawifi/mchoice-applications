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
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import hms.ctap.simulator.sms.SmsMessageReceiver;
import hms.ctap.simulator.ussd.UssdMessageReceiver;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class SimulatorStarter {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8000);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        final ServletHolder servletHolder = new ServletHolder(new AbstractApplicationServlet() {
            @Override
            protected Application getNewApplication(HttpServletRequest request) throws ServletException {
                return new SimulatorApplication();
            }

            @Override
            protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
                return SimulatorApplication.class;
            }
        });
//        servletHolder.setInitParameter("application", "hms.ctap.simulator.SimulatorApplication");
        servletHolder.setInitParameter("widgetset", "hms.ctap.mtracker.plugin.MTrackerWidgetset");
        context.addServlet(servletHolder, "/simulator/*");
        context.addServlet(servletHolder, "/VAADIN/*");
        context.addServlet(new ServletHolder(new UssdMessageReceiver()), "/ussd/");
        context.addServlet(new ServletHolder(new SmsMessageReceiver()), "/sms/");

        server.start();
        server.join();

    }
}