package protodebugger.util.io.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import protodebugger.util.Logger;
import protodebugger.util.io.IConnector;

public class SocketConnector implements IConnector {

	private Socket socket;
	private String hostname;
	private int port;
	private boolean isServer;
	private PrintWriter printer;

	public SocketConnector(String hostname, int port, boolean isServer) {
		this(hostname, port);
		this.isServer = isServer;
	}

	public SocketConnector(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public SocketConnector(int port) {
		this("localhost", port);
	}

	public boolean isConnected(){
		return (socket == null)?true:socket.isConnected();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see protodebugger.util.io.IConnector#connect()
	 */
	@Override
	public boolean connect() {
		boolean ret = false;
		try {
			if (isServer) {
				Logger.INSTANCE.writeInfo("Waiting for client connection...");
				Logger.INSTANCE.writeDebug("Opening a socket at " + hostname
						+ ":" + port);
				socket = new ServerSocket(port).accept();
			} else {
				Logger.INSTANCE.writeInfo("SocketConnecting to " + hostname
						+ ":" + port + "...");
				Logger.INSTANCE.writeDebug("Socket Connecting to " + hostname
						+ ":" + port);
				socket = new Socket(hostname, port);
				printer = new PrintWriter(socket.getOutputStream());
			}
		} catch (IOException e) {
			Logger.INSTANCE.writeError(" Error while creating a connection to "
					+ hostname + ":" + port);
			Logger.INSTANCE.writeDebug(e.getMessage());
		} finally {
			if (socket != null && socket.isConnected()) {
				Logger.INSTANCE.writeInfo("Successfully connected to "
						+ hostname + ":" + port);
				Logger.INSTANCE.writeDebug("Successfully connected to "
						+ hostname + ":" + port);
				ret = true;
			}
			
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see protodebugger.util.io.IConnector#disconnect()
	 */
	@Override
	public boolean disconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see protodebugger.util.io.IConnector#sendMsg(java.lang.Object)
	 */
	@Override
	public boolean sendMsg(Object msg) {
		boolean ret = false;
		if(printer != null){
			Logger.INSTANCE.writeDebug("Sending " + msg);
			printer.println(msg);
			printer.flush();
			ret = true;
		}
		return ret;
	}

	// --------------------------------
	// Getters and Setter
	// --------------------------------

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

}
