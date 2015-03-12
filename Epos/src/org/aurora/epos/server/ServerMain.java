package org.aurora.epos.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main Server Thread. Straight forward socket programming.
 * In case of a connection spawn a request handler for it.
 * @author Emre Ibrahimoglu
 *
 */
public class ServerMain extends Thread {
	private ServerSocket serverSocket;
	private int port;
	private boolean running = false;

	public ServerMain( int port )
	{
		this.port = port;
	}

	public void startServer()
	{
		try
		{
			serverSocket = new ServerSocket( port );
			this.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stopServer()
	{
		running = false;
		this.interrupt();
	}

	@Override
	public void run()
	{
		long startTimer, waitTimer, elapsedTimer;
		int delayTimer=40; //40 milliseconds delay
		
		running = true;
		while( running )
		{
			startTimer=System.currentTimeMillis();
			try
			{
				System.out.println( "Listening for a connection" );
				// Call accept() to receive the next connection
				Socket socket = serverSocket.accept();
				// Pass the socket to the RequestHandler thread for processing
				RequestHandler requestHandler = new RequestHandler( socket );
				requestHandler.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main( String[] args )
	{
		int port=5000;
		if( args.length > 1 )
		{
			System.out.println( "Usage: SimpleSocketServer <port>" );
			System.exit( 0 );
		}
		if( args.length == 1 )
			port = Integer.parseInt( args[ 0 ] );
		System.out.println( "Start server on port: " + port );
		ServerMain server = new ServerMain( port );
		server.startServer();

		// Automatically shutdown in 1 minute
		try
		{
			Thread.sleep( 60000 );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		server.stopServer();
	}
}
