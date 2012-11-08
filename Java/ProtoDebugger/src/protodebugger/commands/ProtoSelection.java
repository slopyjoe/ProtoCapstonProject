package protodebugger.commands;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.test.AddressBookProtos.AddressBook;
import org.test.AlienSpeciesProto.AlienSpecies;
import org.test.Simple;

import protodebugger.util.ParseProtoMessage;

import com.google.protobuf.GeneratedMessage;

public class ProtoSelection extends AbstractHandler {

	private ElementListSelectionDialog dialog;
	private Map<String, GeneratedMessage> messageList = new LinkedHashMap<String, GeneratedMessage>();
	{
		messageList.put("AddressBook", AddressBook.getDefaultInstance());
		messageList.put("AlienSpecies", AlienSpecies.getDefaultInstance());
	}
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(dialog == null){
			dialog = new ElementListSelectionDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell()
					, new LabelProvider());
			
			dialog.setElements(messageList.keySet().toArray());
			dialog.setTitle("Select Proto Message");
			dialog.setMessage("Select a String (* = any string, ? = any char) :");
		}
		
		if (dialog.open() == Window.OK){
			if(messageList.get(dialog.getFirstResult()) != null)
			ParseProtoMessage.INSTANCE.selectionChange(
					messageList.get(dialog.getFirstResult()));
		}
		return null;
	}

	

}
