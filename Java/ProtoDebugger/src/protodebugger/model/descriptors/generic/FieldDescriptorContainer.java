package protodebugger.model.descriptors.generic;

import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class FieldDescriptorContainer<V> implements IFieldDescriptor<V>{
	
	protected V v;
	protected Descriptors.FieldDescriptor protoField;
	protected String name;
	
	public FieldDescriptorContainer(Descriptors.FieldDescriptor protoField){
		this.protoField = protoField;
		name = protoField.getName();
	}


	@Override
	public V getValue() {
		return v;
	}

	/**
	 * Does nothing since the proto field
	 * does not have a default value.
	 */
	@Override
	public void setDefaultValue(V v) {
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		
		if(v == null)
		{
			return false;
		}
		else
		{
			builder.setField(protoField, v);
			return true;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public Widget getWidget(){
		throw new RuntimeException("");
		//return null;
	}
}
