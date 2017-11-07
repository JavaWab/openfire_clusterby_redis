package org.jivesoftware.openfire.session;

import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;

import java.util.Collection;

public interface RedisSessionCompent {
    void updateClientSessionPresence(Presence presence);

    void addClientSession(JID route, RedisClientSessionEntity entity);

    void addUserSession(JID route, Collection<String> jids);

    void removeClientSession(JID route);

    void removeUserSession(JID route);

    void userStatusNotify(Presence route);

    void addOnlineCustomerFlag(String customerUsername);

    void removeOnlineCustomerFlag(String customerUsername);
}
