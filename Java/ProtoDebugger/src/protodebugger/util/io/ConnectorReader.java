package protodebugger.util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import protodebugger.util.Logger;

public class ConnectorReader implements Runnable {

	private InputStream inputStream;
	public ConnectorReader(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	@Override
	public void run() {
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(inputStream));
		
		try {
			String line;
			while((line = reader.readLine()) != null)
			{
				Logger.INSTANCE.writeInfo("Read in: " + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}
