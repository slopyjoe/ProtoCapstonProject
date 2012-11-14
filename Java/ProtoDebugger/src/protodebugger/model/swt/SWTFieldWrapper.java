package protodebugger.model.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import protodebugger.util.Logger;
import protodebugger.model.descriptors.IFieldDescriptor;

public abstract class SWTFieldWrapper {

	protected Widget swtWidget;
	private IFieldDescriptor<?> innerField;
	public SWTFieldWrapper(Widget widget)
	{
		swtWidget = widget;
		swtWidget.addListener(SWT.MouseExit | SWT.CHANGED, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
			Logger.INSTANCE.writeDebug("Change Focus");
				widgetValueToProtoField();
			}
		});
	}
	
	public Widget getSwtWidget() {
		return swtWidget;
	}
	
	public void setFieldDescriptor(IFieldDescriptor<?> innerField){
		this.innerField = innerField;
	}
	
	public void widgetValueToProtoField(){
		if(innerField != null)
			widgetValueToProtoField(innerField);
	}
	public void protoValueToWidget(){
		if(innerField != null)
			protoValueToWidget(innerField);
	}
	
	public abstract void widgetValueToProtoField(IFieldDescriptor<?> field);
	public abstract void protoValueToWidget(IFieldDescriptor<?> field);

}
