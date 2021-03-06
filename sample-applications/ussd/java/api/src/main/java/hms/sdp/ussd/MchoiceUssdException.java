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
package hms.sdp.ussd;

/**
 * $LastChangedDate$
 * $LastChangedBy$
 * $LastChangedRevision$
 */
public class MchoiceUssdException extends Exception {

    public MchoiceUssdException() {
        super();
    }

    public MchoiceUssdException(String message) {
        super(message);
    }

    public MchoiceUssdException(String message, Throwable cause) {
        super(message, cause);
    }

    public MchoiceUssdException(Throwable cause) {
        super(cause);
    }
}
