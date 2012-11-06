package protodebugger.views;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import protodebugger.model.descriptors.FieldDescriptorContainer;
import protodebugger.model.descriptors.MessageFieldDescriptorContainer;
import protodebugger.util.ParseProtoMessage;

import com.google.protobuf.GeneratedMessage;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ProtoViewer extends ViewPart implements PropertyChangeListener{

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "protodebugger.views.ProtoViewer";

	private ExpandBar expandBar;
	private ExpandBar currentBar;
	private int SPACING = 5;
	
	

	/**
	 * The constructor.
	 */
	public ProtoViewer() {
		ParseProtoMessage.INSTANCE.addChangeListener(this);
		
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		expandBar = new ExpandBar(parent, SWT.BORDER | SWT.V_SCROLL);
		expandBar.setSpacing(SPACING);
	}

	private void addSubField(FieldDescriptorContainer field)
	{
		ExpandItem item = new ExpandItem(currentBar, SWT.NONE);
		item.setText(field.getName().replace("_", " "));
		item.setData(field);
		item.setExpanded(false);
		item.setControl((Control)field.getWidget(currentBar));
		item.setHeight(item.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}
	public void selectionChanged(GeneratedMessage msg)
	{
		for(ExpandItem item : expandBar.getItems())
		{
			item.setExpanded(false);
			item.dispose();
		}
		createExpandItems(msg);
	}
	private void createExpandItems(GeneratedMessage msg)
	{
		expandBar.setData(msg);
		currentBar = expandBar;
		List<FieldDescriptorContainer> fields =
			ParseProtoMessage.INSTANCE.getListforMsg(msg);
		
		for(FieldDescriptorContainer field : fields)
		{
			if(field instanceof MessageFieldDescriptorContainer)
			{
				addMessageField((MessageFieldDescriptorContainer)field);
				currentBar = expandBar;
			}
			else
			{
				addSubField(field);
			}
		}
	}
	private void addMessageField(MessageFieldDescriptorContainer field)
	{
		final ExpandItem item = new ExpandItem(currentBar, SWT.NONE);
		item.setText(field.getName().replace("_"," "));
		item.setData(field);
		item.setExpanded(false);
		final ExpandBar innerBar = new ExpandBar(currentBar, SWT.NONE);
		currentBar = innerBar;
		innerBar.setSpacing(SPACING);
		item.setControl(innerBar);
		field.setParent(innerBar);
		for(FieldDescriptorContainer innerField: field.getMembers())
		{
			if(innerField instanceof MessageFieldDescriptorContainer)
			{
				addMessageField((MessageFieldDescriptorContainer) innerField);
				currentBar = innerBar;
			}
			else
			{
				addSubField(innerField);
			}
		}
		item.setHeight(item.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		innerBar.addExpandListener(new ExpandListener() {
			
			@Override
			public void itemExpanded(ExpandEvent arg0) {
				updateBar();				
			}
			
			@Override
			public void itemCollapsed(ExpandEvent arg0) {
				updateBar();
			}
			private void updateBar()
			{
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.getDisplay().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							int height = 0;
							for(ExpandItem nestedItem :innerBar.getItems())
							{
								height += nestedItem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y +
										nestedItem.getHeight();
							}
							item.setHeight(height);
						}
					});
			}
		});
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() 
	{
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
		if(evt.getPropertyName().equals("PROTO_CHANGE")||
				evt.getPropertyName().equals("REMOVE_FIELD"))
		{
			GeneratedMessage msg = (GeneratedMessage)evt.getNewValue();
			selectionChanged(msg);
		}
		else if(evt.getPropertyName().equals("REPEATED_FIELD"))
		{
			FieldDescriptorContainer field = (FieldDescriptorContainer)evt.getOldValue();
			Composite comp = field.getParent();
			if(comp != null)
			{
				currentBar = (ExpandBar)comp;
				FieldDescriptorContainer added =
					(FieldDescriptorContainer) evt.getNewValue();
				if(added instanceof MessageFieldDescriptorContainer)
					addMessageField((MessageFieldDescriptorContainer)added);
				else
					addSubField(added);
			}
		}
	}

}