package protodebugger.model.descriptors.generic;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public abstract class AbstractFieldDescriptor<V> implements IFieldDescriptor<V> {
	
	protected V v;
	protected Descriptors.FieldDescriptor protoField;
	protected String name;
	
	public AbstractFieldDescriptor(Descriptors.FieldDescriptor protoField){
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
	public void setDefaultValue(V v) {
		if(protoField.hasDefaultValue())
			v = (V) protoField.getDefaultValue();
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		
		if(v == null)
		{
			return false;
		}
		else if(protoField.isRepeated())
		{
			builder.addRepeatedField(protoField, v);
			return true;
		}
		else
		{
			builder.setField(protoField, v);
			return true;
		}
	}
	
	@Override
	public boolean isRepeated()
	{
		return protoField.isRepeated();
	}

	@Override
	public String getName() {
		return name;
	}
}
