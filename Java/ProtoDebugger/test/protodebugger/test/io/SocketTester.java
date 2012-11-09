package protodebugger.test.io;

import protodebugger.util.io.socket.SocketConnector;

public class SocketTester {

	static SocketConnector connector;
	public static void serverTesting()
	{
		connector = new SocketConnector(2005);
		connector.setServer(true);
		connector.connect();
	}
	public static void clientTesting()
	{
		connector = new SocketConnector(2005);
		connector.connect();
		try {
			Thread.sleep(6000);
			connector.sendMsg("Client is connected");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){

		boolean isServer = false;
		for(int i =0; i < args.length ; i++){
			if(args[i].equals("server"))
			{
				isServer = true;
				break;
			}
		}
		if(isServer)
			serverTesting();
		else
			clientTesting();
		while(true){
			try {
				Thread.sleep(1000);
				connector.sendMsg("Jello");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
