<<<<<<< HEAD
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
=======
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
>>>>>>> 5a14f9bb2eb2e5da316d0185feeade7969c536bd
