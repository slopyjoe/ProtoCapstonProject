<<<<<<< HEAD
package protodebugger.model.descriptors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;


public class TextFieldDescriptorContainer extends FieldDescriptorContainer {
	private Text textField;
	
	public TextFieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		super(field);
	}
	@Override
	public Object getValue() {
		if(value != null)
			return value.toString();
		else
			return "";
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		if(field.isOptional() && textField.getText().equals(""))
			return false;
		if(field.isRepeated())
			build.addRepeatedField(field, textField.getText());
		else
			build.setField(field, textField.getText());
		return true;
			
	}

	@Override
	public String toString() {
		return "TextField name = '" + name + "'";
	}

	@Override
	public Widget getWidget(Composite parent) {
		if(textField == null)
		{
			textField = new Text(parent, SWT.BORDER);
			textField.setText((String)getValue());
		}
		else if(textField.getParent() != parent)
		{
			textField.setParent(parent);
		}
		return textField;
		
	}

	@Override
	public Composite getParent() {
		if(textField != null)
			return textField.getParent();
		return null;
	}

	
}
=======
package protodebugger.model.descriptors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;


public class TextFieldDescriptorContainer extends FieldDescriptorContainer {
	private Text textField;
	
	public TextFieldDescriptorContainer(Descriptors.FieldDescriptor field)
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
			return "";
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		if(field.isOptional() && textField.getText().equals(""))
			return false;
		if(field.isRepeated())
			build.addRepeatedField(field, textField.getText());
		else
			build.setField(field, textField.getText());
		return true;
			
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TextField name = '" + name + "'";
	}

	@Override
	public Widget getWidget(Composite parent) {
		if(textField == null)
		{
			textField = new Text(parent, SWT.BORDER);
			textField.setText((String)getValue());
		}
		else if(textField.getParent() != parent)
		{
			textField.setParent(parent);
		}
		return textField;
		
	}

	@Override
	public Composite getParent() {
		if(textField != null)
			return textField.getParent();
		return null;
	}

}
>>>>>>> 5a14f9bb2eb2e5da316d0185feeade7969c536bd
