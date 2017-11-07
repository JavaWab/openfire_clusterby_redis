package org.jivesoftware.openfire.plugin.rest.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "message_entities")

public class MessageHistoryEntities {
    /**
     * The messages.
     */
    List<MessageHistoryEntity> messages;
    private long pagesTotal;

    public MessageHistoryEntities() {
    }

    public MessageHistoryEntities(List<MessageHistoryEntity> messages, long pagesTotal) {
        this.pagesTotal = pagesTotal;
        this.messages = messages;
    }
    @XmlElement(name = "message")
    public List<MessageHistoryEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageHistoryEntity> messages) {
        this.messages = messages;
    }

    @XmlElement(name = "pagesTotal")
    public long getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(long pagesTotal) {
        this.pagesTotal = pagesTotal;
    }
}
