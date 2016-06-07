package com.tch.test.learn.june.tigase.plugin;

import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

import tigase.db.NonAuthUserRepository;
import tigase.server.Packet;
import tigase.xmpp.BareJID;
import tigase.xmpp.JID;
import tigase.xmpp.XMPPException;
import tigase.xmpp.XMPPProcessorIfc;
import tigase.xmpp.XMPPResourceConnection;
import tigase.xmpp.impl.annotation.AnnotatedXMPPProcessor;
import tigase.xmpp.impl.annotation.Handle;
import tigase.xmpp.impl.annotation.Handles;
import tigase.xmpp.impl.annotation.Id;

@Id("myplugintest")
@Handles({ @Handle(path = { "message" }, xmlns = "jabber:client") })
public class MyPluginTest extends AnnotatedXMPPProcessor implements XMPPProcessorIfc {

	private static final Logger logger = Logger.getLogger(MyPluginTest.class.getName());

	public void process(Packet packet, XMPPResourceConnection session, NonAuthUserRepository repo,
			Queue<Packet> results, Map<String, Object> settings) throws XMPPException {
		logger.info("session.getjid(): "+ session.getjid());
		BareJID id = (packet.getStanzaTo() != null)
				? packet.getStanzaTo().getBareJID()
				: null;
		logger.info("packet.getStanzaTo().getBareJID(): "+ id);
		logger.info("session.isUserId(packet.getStanzaTo().getBareJID()): " + session.isUserId(id));
		
		id = (packet.getStanzaFrom() != null)
				? packet.getStanzaFrom().getBareJID()
				: null;
		logger.info("packet.getStanzaFrom().getBareJID(): "+ id);
		logger.info("session.isUserId(packet.getStanzaFrom().getBareJID()): " + session.isUserId(id));
		
		JID jid = packet.getFrom();
		logger.info("packet.getFrom(): "+jid);
		logger.info("session.getConnectionId().equals(jid): " + session.getConnectionId().equals(jid));
		
		jid = packet.getTo();
		logger.info("packet.getTo(): "+jid);
		logger.info("session.getConnectionId().equals(jid): " + session.getConnectionId().equals(jid));
	}

}
