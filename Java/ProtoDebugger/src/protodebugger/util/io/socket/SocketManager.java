package protodebugger.util.io.socket;

import javax.swing.SwingUtilities;

public enum SocketManager {

	INSTANCE;
	
	private SocketConnector socketDevice;
	
	public void connect(String host, int port){
		socketDevice = new SocketConnector(host, port);
		if(!socketDevice.connect())
			socketDevice = null;
	}
	
	public boolean isConnected(){
		return (socketDevice == null)?false:socketDevice.isConnected();
	}
	//TODO don't send messages like this
	public void sendMessage(final Object msg){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				socketDevice.sendMsg(msg);
			}
		});
	}
	
}
