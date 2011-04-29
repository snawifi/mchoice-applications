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
package hms.ctap.simulator.sms;

/**
 * Created by IntelliJ IDEA.
 * User: hms
 * Date: 4/27/11
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmsAoRequestMessage {

    private String address;
    private String message;
    private String appId;
    private String password;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SmsAoRequestMessage");
        sb.append("{address='").append(address).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
