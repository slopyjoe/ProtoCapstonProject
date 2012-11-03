package protodebugger.util.io.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Socket sock = new Socket("localhost", 2005);
			BufferedOutputStream buf ;
			
			Scanner scan = new Scanner(System.in);
			String line = "";
			System.out.print("Please enter a message: ");
			while ((line = scan.next()) != null)
			{
				System.out.println("Sending " + line + " to server.");
				buf = new BufferedOutputStream(sock.getOutputStream(), 20);
				buf.write(line.getBytes());
				buf.flush();
				if(line.equals("exit"))
				{
					break;
				}
				System.out.println("Please enter a message: ");
			}
			
			scan.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
