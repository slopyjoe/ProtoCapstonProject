package protodebugger.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.AbstractHandler;

import protodebugger.test.packages.ProtoPackages;
import protodebugger.util.ProtoWriter;
import protodebugger.controller.ViewerController;

public class Save extends AbstractHandler
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		ProtoWriter.writeProto(ViewerController.INSTANCE.getModel().getPackages());
		return null;
	}
}
