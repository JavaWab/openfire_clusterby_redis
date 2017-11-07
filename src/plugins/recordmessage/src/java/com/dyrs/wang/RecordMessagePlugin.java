package com.dyrs.wang;

import com.dyrs.wang.utils.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.net.UnknownHostException;
import java.util.UUID;

public class RecordMessagePlugin implements PacketInterceptor, Plugin {
    private InterceptorManager interceptorManager;
    private XMPPServer server;

    public RecordMessagePlugin() {
        this.interceptorManager = InterceptorManager.getInstance();
        this.server = XMPPServer.getInstance();
    }

    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        interceptorManager.addInterceptor(this);
        System.out.println("initializing... RecordMessagePlugin!");
    }

    @Override
    public void destroyPlugin() {
        interceptorManager.removeInterceptor(this);
        System.out.println("server stop，destroy RecordMessagePlugin!");
    }

    @Override
    public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {
        if (packet instanceof Message) {
            String body = ((Message) packet).getBody();

            if (incoming == true && processed == false && body != null) {
                Jedis jedis = null;
                try {
                    jedis = server.getJedisPool().getResource();
                    String msg_id = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
                    JSONObject jsonObject = new JSONObject(body);
                    jsonObject.put("msg_id", msg_id);
                    String ip = jedis.hget("client_ips", packet.getFrom().toBareJID());
                    if (ip == null || ip.length() < 1){
                        try {
                            ip = session.getHostAddress();
                        } catch (UnknownHostException e) {
                            ip = "unknown";
                        }
                    }
                    jsonObject.put("ip",ip);
                    String form_uid = jsonObject.optString("from_uid");
                    String to_uid = jsonObject.optString("to_uid");

                    jedis.zadd("message:" + StringUtils.makeNewString(form_uid, to_uid), System.currentTimeMillis(), jsonObject.toString());
                } catch (JSONException e) {
                    System.out.println("message body is not JSONObject --> " + body);
                }finally {
                    if (jedis != null)
                        jedis.close();
                }

            }
        }
    }
}
