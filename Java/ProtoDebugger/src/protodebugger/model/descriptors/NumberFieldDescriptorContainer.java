package protodebugger.model.descriptors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;


import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class NumberFieldDescriptorContainer extends FieldDescriptorContainer {

	private Text textField;
	public NumberFieldDescriptorContainer(Descriptors.FieldDescriptor field)
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
		
		if(textField.getText().isEmpty() && field.isOptional())
			return false;
		Object obj = 1;
		try {
			switch (field.getJavaType()) {
			case INT:
				obj = Integer.valueOf(textField.getText());
				break;
			case LONG:
				obj = Long.valueOf(textField.getText());
				break;
			case DOUBLE:
				obj = Double.valueOf(textField.getText());
				break;
			case FLOAT:
				obj = Float.valueOf(textField.getText());
				break;
			default:
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		if(field.isRepeated())
			build.addRepeatedField(field, obj);
		else
			build.setField(field, obj);
		return true;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NumberField name = " + name;
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
