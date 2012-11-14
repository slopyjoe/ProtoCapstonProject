package protodebugger.util;

import java.io.IOException;

import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public enum Logger {

	INSTANCE;
	private MessageConsole protoConsole;

	private Logger()
	{
		protoConsole = new MessageConsole("PROTO CONSOLE", null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{protoConsole});
	}

	public void writeInfo(String msg){
		MessageConsoleStream stream = protoConsole.newMessageStream();
		stream.println("INFO - "+ msg);
		try {
		stream.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	public void writeError(String msg){
		MessageConsoleStream stream = protoConsole.newMessageStream();
		stream.println("ERROR - "+msg);
		try {
		stream.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	public void writeDebug(String msg){
		MessageConsoleStream stream = protoConsole.newMessageStream();
		stream.println("Debug - "+msg);
		try {
		stream.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
}
