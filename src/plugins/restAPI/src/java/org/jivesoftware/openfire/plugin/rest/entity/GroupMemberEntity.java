package org.jivesoftware.openfire.plugin.rest.entity;

import org.xmpp.packet.JID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "member")
@XmlType(propOrder = { "username", "nick", "icon", "sessionStatus", "jid" })
public class GroupMemberEntity {

    private String username;
    private String nick;
    private String icon;
    private String sessionStatus;
    private String jid;

    public GroupMemberEntity() {
    }

    public GroupMemberEntity(String username, String icon, String nick, String sessionStatus, String jid) {
        this.username = username;
        this.nick = nick;
        this.icon = icon;
        this.sessionStatus = sessionStatus;
        this.jid = jid;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @XmlElement
    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    @XmlElement
    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    @XmlElement
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
