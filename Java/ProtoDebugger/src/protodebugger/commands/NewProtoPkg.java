package protodebugger.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import protodebugger.controller.ViewerController;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.views.NewProtoPkgDialog;

public class NewProtoPkg extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		NewProtoPkgDialog protoDialog = new NewProtoPkgDialog();
		protoDialog.create();
		if(protoDialog.open() == NewProtoPkgDialog.OK)
		{
			ProtoPackage.Builder builder = ProtoPackage.newBuilder();
			builder.setFilePath(protoDialog.getPath());
			builder.setName(protoDialog.getName());
			
			ViewerController.INSTANCE.addProtoPkg(builder.build());
			
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
