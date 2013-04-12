package org.aurora.epos.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;

public class EposNetListener implements MessageListener<HostedConnection>, ConnectionListener {
	EposServerMain myApp;
	Server myServer;
	
	public EposNetListener (EposServerMain app, Server server) {
		myApp = app;
		myServer = server;
		myServer.addConnectionListener(this);
		myServer.addMessageListener(this);
	}
	@Override
	public void connectionAdded(Server serverr, HostedConnection client) {
        int clientId = (int) client.getId();
        Logger.getLogger(EposNetListener.class.getName()).log(Level.WARNING, "Client connection: "+client.toString());
	}

	@Override
	public void connectionRemoved(Server serverr, HostedConnection client) {
        Logger.getLogger(EposNetListener.class.getName()).log(Level.WARNING, "Client removed: "+client.toString());
	}

	@Override
	public void messageReceived(HostedConnection serverr, Message msg) {
        Logger.getLogger(EposNetListener.class.getName()).log(Level.WARNING, "Client message: "+msg.toString());
	}

}
