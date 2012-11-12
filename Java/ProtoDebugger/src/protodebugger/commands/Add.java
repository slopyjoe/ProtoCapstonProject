package protodebugger.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;


public class Add extends AbstractHandler  
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		System.out.println("Inside add command");
		return null;
	}

}
