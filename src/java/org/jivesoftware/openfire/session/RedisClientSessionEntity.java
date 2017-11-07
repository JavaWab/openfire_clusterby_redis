package org.jivesoftware.openfire.session;

public class RedisClientSessionEntity {
    private String domain;
    private String userNode;
    private String serverIP;
    private String presence;
    private String serverNode;
    private String hostAddress;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserNode() {
        return userNode;
    }

    public void setUserNode(String userNode) {
        this.userNode = userNode;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getServerNode() {
        return serverNode;
    }

    public void setServerNode(String serverNode) {
        this.serverNode = serverNode;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public RedisClientSessionEntity(String domain, String userNode, String serverIP, String presence, String serverNode, String hostAddress) {
        this.domain = domain;
        this.userNode = userNode;
        this.serverIP = serverIP;
        this.presence = presence;
        this.serverNode = serverNode;
        this.hostAddress = hostAddress;
    }
}
