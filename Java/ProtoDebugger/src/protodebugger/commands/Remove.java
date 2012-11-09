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

public class Remove extends AbstractHandler 
{
	private ElementListSelectionDialog dialog;
	private Map<String, FieldDescriptorContainer> messageList = new LinkedHashMap<String, FieldDescriptorContainer>();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		if(dialog == null){
			dialog = new ElementListSelectionDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), new LabelProvider());
			if(ParseProtoMessage.INSTANCE.getAddedforMsg() == null || ParseProtoMessage.INSTANCE.getAddedforMsg().isEmpty())
			{
				dialog.setTitle("No Added Fields to Remove");
				dialog.setMessage("There are no added fields");
				
			}
			else
			{
				for(FieldDescriptorContainer field :ParseProtoMessage.INSTANCE.getAddedforMsg())
				{
						messageList.put(field.getName(), field);
				}
				dialog.setElements(messageList.keySet().toArray());
				dialog.setTitle("Select a Message Field to Remove");
				dialog.setMessage("Select a String (* = any string, ? = any char) :");
			}
		}
		if (dialog.open() == Window.OK)
		{
			if(messageList.get(dialog.getFirstResult()) != null)
				ParseProtoMessage.INSTANCE.removeAddedRepeatedField(messageList.get(dialog.getFirstResult()));
		}
		messageList.clear();
		dialog = null;
		return null;
	}

}
