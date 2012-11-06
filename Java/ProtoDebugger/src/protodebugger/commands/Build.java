package protodebugger.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import protodebugger.util.ParseProtoMessage;

public class Build extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ParseProtoMessage.INSTANCE.sendProtoToConsole();
		return null;
	}

}
