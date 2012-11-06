package protodebugger.model.descriptors.generic;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class RepeatedFieldDescriptor<V> extends FieldDescriptorContainer<V> {

	public RepeatedFieldDescriptor(Descriptors.FieldDescriptor protoField) {
		super(protoField);
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {

		if (v == null) {
			return false;
		} else {
			builder.addRepeatedField(protoField, v);
			return true;
		}
	}

}
