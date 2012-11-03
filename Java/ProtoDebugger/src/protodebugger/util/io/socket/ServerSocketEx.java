package protodebugger.util.io.socket;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSocketEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			Socket sock = new ServerSocket(2005).accept();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			while((line = reader.readLine()  ) != null)
			{
				System.out.println("Read in " + line);
				if(line.equals("exit"))
					break;
			}
			reader.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
