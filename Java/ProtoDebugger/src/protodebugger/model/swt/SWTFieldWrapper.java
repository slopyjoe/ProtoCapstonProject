package protodebugger.model.swt;

import org.eclipse.swt.widgets.Widget;

import protodebugger.model.descriptors.generic.IFieldDescriptor;

public abstract class SWTFieldWrapper {

	protected Widget swtWidget;
	private IFieldDescriptor<?> innerField;
	public SWTFieldWrapper(Widget widget)
	{
		swtWidget = widget;
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
