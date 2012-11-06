package protodebugger.commands;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import protodebugger.model.descriptors.FieldDescriptorContainer;
import protodebugger.util.ParseProtoMessage;


public class Add extends AbstractHandler  
{
	private ElementListSelectionDialog dialog;
	private Map<String, FieldDescriptorContainer> messageList = new LinkedHashMap<String, FieldDescriptorContainer>();;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		if(dialog == null){
			dialog = new ElementListSelectionDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), new LabelProvider());	
			if(ParseProtoMessage.INSTANCE.getRepeatedforMsg() == null || ParseProtoMessage.INSTANCE.getRepeatedforMsg().isEmpty())
			{
				dialog.setTitle("No Repeated Fields to Add");
				dialog.setMessage("This proto does not contain any valid repeated fields");
				
			}
			else
			{
				for(FieldDescriptorContainer field : ParseProtoMessage.INSTANCE.getRepeatedforMsg())
				{
						messageList.put(field.name, field);
				}
				dialog.setElements(messageList.keySet().toArray());
				dialog.setTitle("Select a Message Field to Add");
				dialog.setMessage("Select a String (* = any string, ? = any char) :");
			}
		}
		if (dialog.open() == Window.OK)
		{
			if(messageList.get(dialog.getFirstResult()) != null)
				ParseProtoMessage.INSTANCE.addRepeatedField(messageList.get(dialog.getFirstResult()));
		}
		messageList.clear();
		dialog = null;
		return null;
	}

}
