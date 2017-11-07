package org.jivesoftware.openfire.plugin.rest.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.rest.controller.MessageController;
import org.jivesoftware.openfire.plugin.rest.entity.MessageEntity;
import org.jivesoftware.openfire.plugin.rest.entity.MessageHistoryEntities;
import org.jivesoftware.openfire.plugin.rest.entity.MessageHistoryEntity;
import org.jivesoftware.openfire.plugin.rest.entity.vo.MessageVO;
import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;
import org.jivesoftware.openfire.plugin.rest.utils.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("restapi/v1/messages")
public class MessageService {

    private MessageController messageController;
    private Jedis jedis;

    @PostConstruct
    public void init() {
        messageController = MessageController.getInstance();
        jedis = XMPPServer.getInstance().getJedisPool().getResource();
    }

    @POST
    @Path("/users")
    public Response sendBroadcastMessage(MessageEntity messageEntity) throws ServiceException {
        messageController.sendBroadcastMessage(messageEntity);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MessageHistoryEntities getMessageList(@QueryParam("from") String from,@QueryParam("to") String to,@QueryParam("page") int page) throws ServiceException {
        if (from == null || to == null ){
            throw new ServiceException("Parameter shortage", "messages", ExceptionType.PROPERTY_NOT_FOUND, Response.Status.BAD_REQUEST);
        }
        MessageVO messageVO = new MessageVO(from, to, page);
        String key = "message:" + StringUtils.makeNewString(messageVO.getFrom(), messageVO.getTo());
        long messageCount = jedis.zcard(key);
        Set<String> results = jedis.zrevrangeByScore(key, "+inf", "-inf", (page -1) * 30, 30);
        List<MessageHistoryEntity> messages = new ArrayList<>();
        for (String josn : results) {
            try {
                // get Attributes by json string
                JSONObject jsonObject = new JSONObject(josn);
                String msg_id = jsonObject.optString("msg_id");
                String form_ijd = jsonObject.optString("form_jid");
                String form_nick = jsonObject.optString("form_nick");
                String form_uid = jsonObject.optString("form_uid");
                String form_icon = jsonObject.optString("form_icon");
                String type = jsonObject.optString("type");
                String message = jsonObject.optString("message");
                String to_jid = jsonObject.optString("to_jid");
                String to_uid = jsonObject.optString("to_uid");
                String to_icon = jsonObject.optString("to_icon");
                String to_nick = jsonObject.optString("to_nick");
                long time = jsonObject.optLong("time");
                // make message history entity
                MessageHistoryEntity messageHistoryEntity = new MessageHistoryEntity();
                messageHistoryEntity.setFormIcon(form_icon);
                messageHistoryEntity.setFormJID(form_ijd);
                messageHistoryEntity.setFormNick(form_nick);
                messageHistoryEntity.setFormUID(form_uid);
                messageHistoryEntity.setMessage(message);
                messageHistoryEntity.setMsgID(msg_id);
                messageHistoryEntity.setTime(time);
                messageHistoryEntity.setToIcon(to_icon);
                messageHistoryEntity.setToUID(to_uid);
                messageHistoryEntity.setToJID(to_jid);
                messageHistoryEntity.setType(type);
                messageHistoryEntity.setToNick(to_nick);
                // add entity to list
                messages.add(messageHistoryEntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new MessageHistoryEntities(messages, (messageCount%30)>0? (messageCount/30) + 1:(messageCount/30));
    }

    @PreDestroy
    public void destroy() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
