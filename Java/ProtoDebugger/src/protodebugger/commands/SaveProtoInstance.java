package protodebugger.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import protodebugger.controller.EditorController;

public class SaveProtoInstance extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EditorController.INSTANCE.saveCurrent();
		return null;
	}

}
