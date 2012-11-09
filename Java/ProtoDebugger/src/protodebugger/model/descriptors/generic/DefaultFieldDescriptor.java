package protodebugger.model.descriptors.generic;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class DefaultFieldDescriptor<V> extends FieldDescriptorContainer<V> {

	protected V v;
	protected Descriptors.FieldDescriptor protoField;
	protected String name;
	
	@SuppressWarnings("unchecked")
	public DefaultFieldDescriptor(Descriptors.FieldDescriptor protoField){
		super(protoField);
		v = (V) protoField.getDefaultValue();
	}

	public String getName()
	{
		return name;
	}

	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setDefaultValue(V v) {
		v = (V) protoField.getDefaultValue();		
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		builder.setField(protoField, v);
		return true;
	}
}
