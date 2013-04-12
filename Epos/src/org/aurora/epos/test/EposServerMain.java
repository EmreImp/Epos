package org.aurora.epos.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;

public class EposServerMain extends SimpleApplication
{
	private static final Logger myLogger = Logger.getLogger(EposServerMain.class.getName());
	private static EposServerMain myApp;
	private static Server myServer;
	private EposNetListener myNetListener;

	public static void main(String[] args){
		myApp = new EposServerMain();
		//ignore all input and audio/visual output...
		myApp.start(JmeContext.Type.Headless);
	}
	
	@Override
	public void simpleInitApp() {
		myLogger.log(Level.WARNING, "TestMonkey Server start.");
		try {
			myServer = Network.createServer(Globals.DEFAULT_PORT_TCP, Globals.DEFAULT_PORT_UDP);
		} catch (IOException e) {
			myLogger.log(Level.SEVERE, "Test Monekey Server creation broke {0}."+e);	
		}
		myLogger.log(Level.WARNING, "TestMonkey Server Start network listener.");	
		myNetListener = new EposNetListener(this, myServer);
	}

	@Override
	public void destroy() {
		super.destroy();
		myServer.close();
		myLogger.log(Level.WARNING, "TestMonkey Server close.");	
	}

}
