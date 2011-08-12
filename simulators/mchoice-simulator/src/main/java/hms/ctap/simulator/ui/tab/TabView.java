/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hms.ctap.simulator.ui.tab;

import com.vaadin.ui.*;

/**
 *
 * @author hms
 */
public abstract class TabView {

    private TextField urlTextField;
    private TextField phoneNoField;
    private TextArea messageField;

    public TabView() {
        init();
    }

    public void init() {
        urlTextField = new TextField();
        urlTextField.setWidth("150px");
        phoneNoField = new TextField();
        phoneNoField.setWidth("150px");
        messageField = new TextArea();
        messageField.setWidth("150px");
    }


    /**
     * @return the Panel containing input text fields
     */
    public Component createInputPanel() {

        Panel inputPanel = new Panel("Send Message");

        inputPanel.addComponent(createPanelLayout("App URL", urlTextField, "http://127.0.0.1:8080/"));
        inputPanel.addComponent(createPanelLayout("Phone #", phoneNoField, "94721345678"));
        inputPanel.addComponent(createPanelLayout("Message ", messageField, "Message Content"));

        Button sendButton = createSendMsgClearButton();
        inputPanel.addComponent(sendButton);
        final AbstractOrderedLayout content = (AbstractOrderedLayout) inputPanel.getContent();
        content.setSpacing(true);
        content.setComponentAlignment(sendButton, Alignment.BOTTOM_RIGHT);

        return inputPanel;
    }

    private HorizontalLayout createPanelLayout(final String msg, final AbstractTextField textField, final String textValue) {
        HorizontalLayout msgLayout = new HorizontalLayout();
        msgLayout.setSpacing(true);
        Label msgLabel = new Label(msg);
        msgLabel.setWidth("65px");
        textField.setWidth("150px");
        msgLayout.addComponent(msgLabel);
        msgLayout.addComponent(textField);
        textField.setValue(textValue);
        return msgLayout;
    }

    /**
     * @return the Send Message Button
     */
    public abstract Button createSendMsgClearButton();


    public TextField getUrlTextField() {
        return urlTextField;
    }

    public TextField getPhoneNoField() {
        return phoneNoField;
    }

    public TextArea getMessageField() {
        return messageField;
    }

    /**
     * @return a Vertical Layout containing the contents inside USSD TabView
     */
    public abstract Component getTabLayout();
}
