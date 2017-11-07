package org.jivesoftware.openfire.plugin.rest.controller;

import java.util.*;

import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.group.GroupAlreadyExistsException;
import org.jivesoftware.openfire.group.GroupManager;
import org.jivesoftware.openfire.group.GroupNotFoundException;
import org.jivesoftware.openfire.plugin.rest.entity.GroupEntity;
import org.jivesoftware.openfire.plugin.rest.entity.GroupMemberEntity;
import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;
import org.jivesoftware.openfire.plugin.rest.utils.MUCRoomUtils;
import org.jivesoftware.openfire.session.ClientSession;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.xmpp.packet.JID;
import org.xmpp.packet.Presence;

/**
 * The Class GroupController.
 */
public class GroupController {
    /**
     * The Constant INSTANCE.
     */
    public static final GroupController INSTANCE = new GroupController();

    /**
     * Gets the single instance of GroupController.
     *
     * @return single instance of GroupController
     */
    public static GroupController getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the groups.
     *
     * @return the groups
     * @throws ServiceException the service exception
     */
    public List<GroupEntity> getGroups() throws ServiceException {
        Collection<Group> groups = GroupManager.getInstance().getGroups();
        List<GroupEntity> groupEntities = new ArrayList<GroupEntity>();
        for (Group group : groups) {
            GroupEntity groupEntity = new GroupEntity(group.getName(), group.getDescription());
            groupEntities.add(groupEntity);
        }

        return groupEntities;
    }

    /**
     * Gets the group.
     *
     * @param groupName the group name
     * @return the group
     * @throws ServiceException the service exception
     */
    public GroupEntity getGroup(String groupName) throws ServiceException, UserNotFoundException {
        Group group;
        SessionManager sessionManager;
        UserManager userManager;
        try {
            group = GroupManager.getInstance().getGroup(groupName);
            sessionManager = SessionManager.getInstance();
            userManager = XMPPServer.getInstance().getUserManager();
        } catch (GroupNotFoundException e) {
            throw new ServiceException("Could not find group", groupName, ExceptionType.GROUP_NOT_FOUND,
                    Response.Status.NOT_FOUND, e);
        }

        GroupEntity groupEntity = new GroupEntity(group.getName(), group.getDescription());
        groupEntity.setAdmins(MUCRoomUtils.convertJIDsToStringList(group.getAdmins()));
        List<GroupMemberEntity> members = new ArrayList<>();
        if (group.getMembers() != null) {
            for (JID jid : group.getMembers()
                    ) {
                String user_name = jid.getNode();
                String nick = userManager.getUser(user_name).getName();
                Collection<ClientSession> sessions = sessionManager.getSessions(user_name);
                String icon = "http://img4.imgtn.bdimg.com/it/u=2286837302,1397695233&fm=214&gp=0.jpg";
                members.add(new GroupMemberEntity(user_name, icon, nick, sessions.isEmpty() ? "Offline" : "Online", jid.toBareJID()));
            }

        }
        Collections.sort(members, new Comparator<GroupMemberEntity>() {
            @Override
            public int compare(GroupMemberEntity o1, GroupMemberEntity o2) {
                try {
                    Integer id1 = Integer.parseInt(o1.getUsername());
                    Integer id2 = Integer.parseInt(o2.getUsername());
                    if (id1 < id2) {
                        return -1;
                    }
                    if (id1 > id2) {
                        return 1;
                    }
                }catch (Exception e) {
                    return o1.getUsername().compareTo(o2.getUsername());
                }
                return 0;
            }
        });
        groupEntity.setMembers(members);

        return groupEntity;
    }

    private String getSessionStatus(ClientSession clientSession) {
        Presence.Show show = clientSession.getPresence().getShow();
        if (show == Presence.Show.away) {
            return "Away";
        } else if (show == Presence.Show.chat) {
            return "Available to Chat";
        } else if (show == Presence.Show.dnd) {
            return "Do Not Disturb";
        } else if (show == Presence.Show.xa) {
            return "Extended Away";
        } else {
            return "Online";
        }
    }

    /**
     * Creates the group.
     *
     * @param groupEntity the group entity
     * @return the group
     * @throws ServiceException the service exception
     */
    public Group createGroup(GroupEntity groupEntity) throws ServiceException {
        Group group;
        if (groupEntity != null && !groupEntity.getName().isEmpty()) {
            try {
                group = GroupManager.getInstance().createGroup(groupEntity.getName());
                group.setDescription(groupEntity.getDescription());

                group.getProperties().put("sharedRoster.showInRoster", "onlyGroup");
                group.getProperties().put("sharedRoster.displayName", groupEntity.getName());
                group.getProperties().put("sharedRoster.groupList", "");
            } catch (GroupAlreadyExistsException e) {
                throw new ServiceException("Could not create a group", groupEntity.getName(),
                        ExceptionType.GROUP_ALREADY_EXISTS, Response.Status.CONFLICT, e);
            }
        } else {
            throw new ServiceException("Could not create new group", "groups",
                    ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
        }
        return group;
    }

    /**
     * Update group.
     *
     * @param groupName   the group name
     * @param groupEntity the group entity
     * @return the group
     * @throws ServiceException the service exception
     */
    public Group updateGroup(String groupName, GroupEntity groupEntity) throws ServiceException {
        Group group;
        if (groupEntity != null && !groupEntity.getName().isEmpty()) {
            if (groupName.equals(groupEntity.getName())) {
                try {
                    group = GroupManager.getInstance().getGroup(groupName);
                    group.setDescription(groupEntity.getDescription());
                } catch (GroupNotFoundException e) {
                    throw new ServiceException("Could not find group", groupName, ExceptionType.GROUP_NOT_FOUND,
                            Response.Status.NOT_FOUND, e);
                }
            } else {
                throw new ServiceException(
                        "Could not update the group. The group name is different to the payload group name.", groupName + " != " + groupEntity.getName(),
                        ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
            }
        } else {
            throw new ServiceException("Could not update new group", "groups",
                    ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
        }
        return group;
    }

    /**
     * Delete group.
     *
     * @param groupName the group name
     * @throws ServiceException the service exception
     */
    public void deleteGroup(String groupName) throws ServiceException {
        try {
            Group group = GroupManager.getInstance().getGroup(groupName);
            GroupManager.getInstance().deleteGroup(group);
        } catch (GroupNotFoundException e) {
            throw new ServiceException("Could not find group", groupName, ExceptionType.GROUP_NOT_FOUND,
                    Response.Status.NOT_FOUND, e);
        }
    }
}
