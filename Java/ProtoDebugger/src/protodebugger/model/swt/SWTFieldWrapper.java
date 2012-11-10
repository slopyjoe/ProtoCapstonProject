package protodebugger.model.swt;

import org.eclipse.swt.widgets.Widget;

import protodebugger.model.descriptors.generic.IFieldDescriptor;

public abstract class SWTFieldWrapper {

	protected Widget swtWidget;
	public SWTFieldWrapper(Widget widget)
	{
		swtWidget = widget;
	}
	
	public Widget getSwtWidget() {
		return swtWidget;
	}
	
	public abstract void widgetValueToProtoField(IFieldDescriptor<?> field);
	public abstract void protoValueToWidget(IFieldDescriptor<?> field);

}
