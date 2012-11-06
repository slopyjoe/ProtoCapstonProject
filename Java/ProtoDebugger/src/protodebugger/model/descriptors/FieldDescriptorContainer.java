<<<<<<< HEAD
package protodebugger.model.descriptors;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public abstract class FieldDescriptorContainer {

	protected final String name; 
	protected Object value;
	protected final Descriptors.FieldDescriptor field;
	protected boolean subField = false;
	protected FieldDescriptorContainer parent;
	
	public FieldDescriptorContainer(final Descriptors.FieldDescriptor field)
	{
		this.field = field;
		this.name = field.getName();
		if(field.hasDefaultValue())
			value = field.getDefaultValue();
	}
	
	
	public abstract boolean buildMsg(Builder<?> build);
	public abstract Object getValue();
	public void setDefaultValue(){
		if(field.hasDefaultValue())
			value = field.getDefaultValue();
	}
	
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
	
	public final String getName(){return name;}
	public final Descriptors.FieldDescriptor getFieldDescriptor(){return field;}
	
	public abstract String toString();
}
=======
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
	public FieldDescriptorContainer getFieldParent()
	{
		if(subField)
			return parent;
		return null;
	}
}
>>>>>>> 5a14f9bb2eb2e5da316d0185feeade7969c536bd
