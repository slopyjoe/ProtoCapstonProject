<<<<<<< HEAD
package protodebugger.model.descriptors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;


import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class BooleanFieldDescriptorContainer extends FieldDescriptorContainer {

	private Button check;
	public BooleanFieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		super(field);
	}
	@Override
	public Object getValue() {
		if(value != null)
			return value.toString();
		else 
			return false;
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		if(field.isRepeated())
			build.addRepeatedField(field, check.getSelection());
		else
			build.setField(field, check.getSelection());
		return true;
	}

	@Override
	public String toString() {
		return "BooleanField name = " + name;
	}

	@Override
	public Widget getWidget(Composite parent) {
		if(check == null)
		{
			check = new Button(parent, SWT.CHECK);
			if(value != null)
				check.setEnabled((Boolean)value);
		}
		else if(check.getParent() != parent)
			check.setParent(parent);
		return check;
	}

	@Override
	public Composite getParent() {
		if(check != null)
			return check.getParent();
		return null;
	}
	

}
=======
package protodebugger.model.descriptors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;


import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class BooleanFieldDescriptorContainer extends FieldDescriptorContainer {

	private Button check;
	public BooleanFieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		super(field);
	}
	@Override
	public Object getValue() {
		if(value != null)
			return value.toString();
		else if(defaultValue != null)
			return defaultValue.toString();
		else 
			return false;
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		if(field.isRepeated())
			build.addRepeatedField(field, check.getSelection());
		else
			build.setField(field, check.getSelection());
		return true;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BooleanField name = " + name;
	}

	@Override
	public Widget getWidget(Composite parent) {
		if(check == null)
			check = new Button(parent, SWT.CHECK);
		else if(check.getParent() != parent)
			check.setParent(parent);
		return check;
	}

	@Override
	public Composite getParent() {
		if(check != null)
			return check.getParent();
		return null;
	}

}
>>>>>>> 5a14f9bb2eb2e5da316d0185feeade7969c536bd
