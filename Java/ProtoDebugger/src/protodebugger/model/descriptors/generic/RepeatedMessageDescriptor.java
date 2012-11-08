package protodebugger.model.descriptors.generic;

import com.google.protobuf.Message;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage.Builder;

public class RepeatedMessageDescriptor extends MessageDescriptor{

	public RepeatedMessageDescriptor(FieldDescriptor protoField) {
		super(protoField);
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		v = (Builder<?>) builder.newBuilderForField(protoField);
		Message builtMsg = getBuiltMsg(builder);
		if(builtMsg == null)
			return false;
		builder.addRepeatedField(protoField, builtMsg);
		return true;
	}
}
