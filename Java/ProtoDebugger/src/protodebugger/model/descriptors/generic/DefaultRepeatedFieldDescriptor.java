package protodebugger.model.descriptors.generic;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage.Builder;

public class DefaultRepeatedFieldDescriptor<V> extends DefaultFieldDescriptor<V> {

	public DefaultRepeatedFieldDescriptor(FieldDescriptor protoField) {
		super(protoField);
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		builder.addRepeatedField(protoField, v);
		return true;
	}


}
