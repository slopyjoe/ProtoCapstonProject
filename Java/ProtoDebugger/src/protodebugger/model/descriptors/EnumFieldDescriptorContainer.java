package protodebugger.model.descriptors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.GeneratedMessage.Builder;


public class EnumFieldDescriptorContainer extends FieldDescriptorContainer {
	private Combo comboField;
	
	public EnumFieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		super(field);
	}
	
	@Override
	public Object getValue() {
		if(value != null)
			return value.toString();
		else if(defaultValue != null)
			return ((EnumValueDescriptor)defaultValue).getName();
		else 
			return field.getEnumType().getValues().get(0).toString();
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		if(field.isOptional() && comboField.getText().equals(""))
			return false;
		if(field.isRepeated())
			build.addRepeatedField(field, field.getEnumType().findValueByName(comboField.getText()));
		else
			build.setField(field, field.getEnumType().findValueByName(comboField.getText()));
		return true;
	}

	@Override
	public void setValue(Object value) {
		if(value instanceof String)
		{
			for(EnumValueDescriptor evd : field.getEnumType().getValues())
			{
				if(evd.getName().equals(value))
				{
					value = evd;
					break;
				}
			}
		}
	}

	@Override
	public String toString() 
	{
		return "EnumField name = " + name;
	}

	@Override
	public Widget getWidget(Composite parent) 
	{
		if(comboField == null)
		{
			comboField = new Combo(parent, SWT.READ_ONLY);
			comboField.setItems(getValues());
			if(defaultValue != null)
				comboField.select(((EnumValueDescriptor)
						defaultValue).getIndex());
		}
		else if(comboField.getParent() != parent)
		{
			comboField.setParent(parent);
		}
		return comboField;
	}
	
	public String[] getValues()
	{
		int arraySize = field.getEnumType().getValues().size();
		if(field.isOptional())
			arraySize += 1;
		String[] ret = new String[arraySize];
		for(EnumValueDescriptor evd : field.getEnumType().getValues())
			ret[evd.getIndex()] = evd.getName();
		if(field.isOptional())
			ret[arraySize-1] = "";
		return ret;
	}

	@Override
	public Composite getParent() {
		if(comboField != null)
			return comboField.getParent();
		return null;
	}

}
