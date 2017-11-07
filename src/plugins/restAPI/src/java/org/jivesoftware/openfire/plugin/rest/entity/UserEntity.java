package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class UserEntity.
 */
@XmlRootElement(name = "user")
@XmlType(propOrder = { "username", "nick", "email", "password","status", "properties" })
public class UserEntity {

	/** The username. */
	private String username;

	/** The name. */
	private String nick;

	/** The email. */
	private String email;

	/** The password. */
	private String password;

	private String status;

	/** The properties. */
	private List<UserProperty> properties;

	/**
	 * Instantiates a new user entity.
	 */
	public UserEntity() {

	}

	/**
	 * Instantiates a new user entity.
	 *
	 * @param username
	 *            the username
	 * @param nick
	 *            the name
	 * @param email
	 *            the email
	 */
	public UserEntity(String username, String nick, String email) {
		this.username = username;
		this.nick = nick;
		this.email = email;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@XmlElement
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@XmlElement
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setNick(String name) {
		this.nick = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	@XmlElement
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	@XmlElement(name = "property")
	@XmlElementWrapper(name = "properties")
	public List<UserProperty> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties
	 *            the new properties
	 */
	public void setProperties(List<UserProperty> properties) {
		this.properties = properties;
	}

	public String getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}
}