package org.jivesoftware.openfire.plugin.rest.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "guest")
@XmlType(propOrder = { "jid", "uid"})
public class GuestAccountEntity {
    private String jid;
    private String uid;

    public GuestAccountEntity() {
    }

    public GuestAccountEntity(String jid, String uid) {
        this.jid = jid;
        this.uid = uid;
    }

    @XmlElement
    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    @XmlElement
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
