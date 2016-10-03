package com.tibco.mcqueary.ems.samples.springjms;

import com.beans.PlayerDetails;
import com.beans.PositionType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;


/**
 * Unit tests for <code>MessageService</code>.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/META-INF/spring/MessageServiceTest-context.xml")
public class MessageServiceTest extends TestCase
{
	@Autowired
	private MessageService messageService;
	private String message;
	
	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		message = "TIBCO EMS test message.";
	}
	
	/**
	 * Test that sends a message to a queue.
	 */
	@Test
	public void testSendMessage() {
		messageService.sendMessage(message);
	}
	
	/**
	 * Test that receives a message from a queue.
	 * 
	 * @throws JMSException
	 */
	@Test
	public void testReadMessage() throws JMSException {
		String readMessage = messageService.readMessage();
		assertEquals(readMessage, message);
	}

	@Test
	public void testSendMessageByObject() throws JMSException {
		PlayerDetails p = new PlayerDetails();
		p.setAge(23);
		p.setName("keuss");
		p.setPosition(PositionType.ATT);
		p.setSurname("Da");
		p.setTeamName("psg");
		messageService.sendMessageByObject(p);
	}
}