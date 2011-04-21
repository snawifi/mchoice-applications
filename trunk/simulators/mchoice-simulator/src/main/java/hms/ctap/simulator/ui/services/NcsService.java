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

import hms.ctap.simulator.ussd.UssdMessageSender;

import java.io.IOException;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public enum NcsService {

    SMS {
        @Override
        void sendMessage(final String url, final String address, final String message) throws IOException {
            UssdMessageSender.getInstance().sendMessage(url, address, message);
        }
    },

    USSD {
        @Override
        void sendMessage(final String url, final String address, final String message) {
            throw new UnsupportedOperationException("Method not implemented...");  //todo must implement method
        }
    };

    abstract void sendMessage(final String url, final String address, final String message) throws IOException;
}
