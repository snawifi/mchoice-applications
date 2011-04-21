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
package hms.ctap.simulator.ui.services;

import com.vaadin.ui.Table;

import java.util.Date;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public interface NcsUIService {

    Table createSentMessageService();

    Table createReceivedMessageService();

    NcsService getNcsService();

    void init();

    void addElementToReceiveTable(int objectId, Date date, String phoneNo, String message, String status);

    void addElementToSentTable(Date date, String phoneNo, String message, String status);
}
