package protodebugger.model.descriptors.generic;

import java.util.Collections;
import java.util.List;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.Message;

public class MessageDescriptor extends FieldDescriptorContainer<Builder<?>> implements IMessageDescriptor<Builder<?>> {

	protected List<IFieldDescriptor<?>> descriptors;
	
	public MessageDescriptor(FieldDescriptor protoField) {
		super(protoField);
	}

	protected Message getBuiltMsg(Builder<?> builder)
	{
		v = (Builder<?>) builder.newBuilderForField(protoField);
		for(IFieldDescriptor<?> descriptor : descriptors)
		{
			if(!descriptor.buildMsg(v))
				return null;
		}
		return v.build();
	}
	
	@Override
	public boolean buildMsg(Builder<?> builder) {
		v = (Builder<?>) builder.newBuilderForField(protoField);
		Message builtMsg = getBuiltMsg(builder);
		if(builtMsg == null)
			return false;
		builder.setField(protoField, builtMsg);
		return true;
	}

	@Override
	public List<IFieldDescriptor<?>> getDescriptors() {
		
		if(descriptors == null)
			return Collections.emptyList();
		return descriptors;
	}

	@Override
	public void addDescriptor(IFieldDescriptor<?> descriptor) {
		this.descriptors.add(descriptor);		
	}

	@Override
	public void removeDescriptor(IFieldDescriptor<?> descriptor) {
		if(descriptors.contains(descriptor))
			descriptors.remove(descriptor);
	}

}
