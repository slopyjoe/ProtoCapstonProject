package protodebugger.commands;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import protodebugger.controller.ViewerController;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.util.ProtoReader;


public class Load extends AbstractHandler 
{
	FileDialog browseFiles;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		browseFiles = new FileDialog(Display.getCurrent().getActiveShell());
		browseFiles.setFilterExtensions(new String[]{"*.protoPkg"});
		File file = new File(browseFiles.open());	
		ProtoPackage result = ProtoReader.readProto(file);
		if( result != null)
		{
			ViewerController.INSTANCE.addProtoPkg(result);
		}
		return null;
	}
}
