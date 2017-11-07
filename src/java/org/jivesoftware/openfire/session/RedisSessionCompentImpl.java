package org.jivesoftware.openfire.session;

import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;

public class RedisSessionCompentImpl implements RedisSessionCompent {
    private JedisPool jedisPool;

    public RedisSessionCompentImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void updateClientSessionPresence(Presence presence){
        Jedis jedis = jedisPool.getResource();
        JID jid = presence.getFrom();
        String key = "ClientSession:" + jid.toBareJID() + ":" + jid.getResource();
        jedis.hset(key, "Presence", presence.toXML());
        jedis.close();
    }

    @Override
    public void addClientSession(JID route, RedisClientSessionEntity destination) {
        Jedis jedis = jedisPool.getResource();
        String key = "ClientSession:" + route.toBareJID() + ":" + route.getResource();
        jedis.hset(key, "Domain", destination.getDomain());
        jedis.hset(key, "UserNode", destination.getUserNode());
        jedis.hset(key, "ServerIP", destination.getServerIP());
        jedis.hset(key, "Presence", destination.getPresence());
        jedis.hset(key, "ServerNode", destination.getServerNode());
        jedis.hset(key, "HostAddress", destination.getHostAddress());
        jedis.close();
    }

    @Override
    public void addUserSession(JID route, Collection<String> jids) {
        Jedis jedis = jedisPool.getResource();
        for (String jid : jids) {
            jedis.hset("UserSession:" + route.toBareJID(), route.getResource(), jid);
        }
        jedis.close();

    }

    @Override
    public void removeUserSession(JID route) {
        Jedis jedis = jedisPool.getResource();
        jedis.hdel("UserSession:" + route.toBareJID(), route.getResource());
        jedis.close();
    }

    @Override
    public void removeClientSession(JID route) {
        Jedis jedis = jedisPool.getResource();
        jedis.del("ClientSession:" + route.toBareJID() + ":" + route.getResource());
        jedis.close();
    }

    @Override
    public void userStatusNotify(Presence presence) {
        Jedis jedis = jedisPool.getResource();
        Presence.Type type = presence.getType();
        if (Presence.Type.unavailable == type) {
            jedis.publish("Offline", presence.getFrom().toFullJID());
        } else {
            jedis.publish("Online", presence.getFrom().toFullJID());
        }
        jedis.close();
    }

    @Override
    public void addOnlineCustomerFlag(String customerUsername) {
        Jedis jedis = jedisPool.getResource();
        jedis.sadd("CustomerServicerSessions:" + customerUsername, customerUsername);
//        jedis.expire("CustomerServicerSessions:" + customerUsername, 43200);  //暂时让数据过期
        jedis.close();
    }

    @Override
    public void removeOnlineCustomerFlag(String customerUsername) {
        Jedis jedis = jedisPool.getResource();
        jedis.del("CustomerServicerSessions:" + customerUsername);
        jedis.close();
    }

}
