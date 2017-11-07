package org.jivesoftware.openfire.plugin.rest.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "message")
@XmlType(propOrder = {"msgID", "formJID", "formNick", "formUID", "formIcon", "message", "toJID", "toUID", "toIcon", "toNick", "type", "time"})
public class MessageHistoryEntity {

    private String msgID;
    private String formJID;
    private String formNick;
    private String formUID;
    private String formIcon;
    private String message;
    private String toJID;
    private String toUID;
    private String toIcon;
    private String toNick;
    private String type;
    private long time;

    public MessageHistoryEntity() {
    }

    @XmlElement
    public String getMsgID() {
        return msgID;
    }

    @XmlElement
    public String getFormJID() {
        return formJID;
    }

    @XmlElement
    public String getFormNick() {
        return formNick;
    }

    @XmlElement
    public String getFormUID() {
        return formUID;
    }

    @XmlElement
    public String getFormIcon() {
        return formIcon;
    }

    @XmlElement
    public String getMessage() {
        return message;
    }

    @XmlElement
    public String getToJID() {
        return toJID;
    }

    @XmlElement
    public String getToUID() {
        return toUID;
    }

    @XmlElement
    public String getToIcon() {
        return toIcon;
    }

    @XmlElement
    public long getTime() {
        return time;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public void setFormJID(String formJID) {
        this.formJID = formJID;
    }

    public void setFormNick(String formNick) {
        this.formNick = formNick;
    }

    public void setFormUID(String formUID) {
        this.formUID = formUID;
    }

    public void setFormIcon(String formIcon) {
        this.formIcon = formIcon;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToJID(String toJID) {
        this.toJID = toJID;
    }

    public void setToUID(String toUID) {
        this.toUID = toUID;
    }

    public void setToIcon(String toIcon) {
        this.toIcon = toIcon;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement
    public String getToNick() {
        return toNick;
    }

    public void setToNick(String toNick) {
        this.toNick = toNick;
    }
}
