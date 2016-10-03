package com.tibco.mcqueary.ems.samples.springjms;

import com.beans.PlayerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * A service that sends and receives JMS messages. 
 *
 */
public class MessageService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	/**
	 * Queue name
	 */
	private String destination;

	/**
	 * Sends a message to a queue.
	 *
	 * @param text Message text.
	 */
	public void sendMessage(final String text) {
		LOGGER.debug("Sending [{}] to queue [{}]", new Object[] { text, destination });
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}
		});
	}

	/**
	 * Receives a message from a queue.
	 *
	 * @return Message text.
	 * @throws JMSException
	 */
	public String readMessage() throws JMSException {
		String message = null;

		Message msg = jmsTemplate.receive(destination);
		if(msg instanceof TextMessage) {
			message = ((TextMessage) msg).getText();
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}

		LOGGER.debug("Read [{}] to queue [{}]", new Object[] { message, destination });

		return message;
	}

	/**
	 * Sends a message to a queue.
	 *
	 * @param object Message.
	 */
	public void sendMessageByObject(final PlayerDetails object) {
		LOGGER.debug("Sending [{}] to queue [{}]", new Object[] { object, destination });
		jmsTemplate.convertAndSend(destination, object);
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}