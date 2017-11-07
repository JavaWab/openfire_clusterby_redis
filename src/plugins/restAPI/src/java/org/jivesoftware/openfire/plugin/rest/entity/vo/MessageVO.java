package org.jivesoftware.openfire.plugin.rest.entity.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "msgvo")
@XmlType(propOrder = { "from", "to", "date" })
public class MessageVO {
    private String from;
    private String to;
    private long date;

    public MessageVO() {
    }

    public MessageVO(String from, String to, long date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @XmlElement
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @XmlElement
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @XmlElement
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
