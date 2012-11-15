package protodebugger.test.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTester {


	public static void main(String args[]){

		try {
			Socket socket = new ServerSocket(2005).accept();
			BufferedReader buff = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String line ;
			while((line  = buff.readLine()) != null){
				System.out.println("Read in " + line);
				
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
