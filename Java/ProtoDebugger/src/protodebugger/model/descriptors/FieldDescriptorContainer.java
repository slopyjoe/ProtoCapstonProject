package protodebugger.model.descriptors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public abstract class FieldDescriptorContainer {

	public String name; 
	protected Object defaultValue, value;
	public Descriptors.FieldDescriptor field;
	protected boolean subField = false;
	protected FieldDescriptorContainer parent;
	public FieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		this.field = field;
		this.name = field.getName();
		if(field.hasDefaultValue())
			defaultValue = field.getDefaultValue();
	}
	
	public abstract Object getValue();
	public abstract boolean buildMsg(Builder<?> build);
	public abstract void setValue(Object value);
	public abstract String toString();
	public abstract Widget getWidget(Composite parent);
	public abstract Composite getParent();
	public boolean isSubField(){return subField;}
	public void setSubField(boolean sub){subField = sub;}
	public void setFieldParent(FieldDescriptorContainer parent){this.parent = parent;}
	public final FieldDescriptorContainer getFieldParent()
	{
		if(subField)
			return parent;
		return null;
	}
}
