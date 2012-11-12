package protodebugger.model.descriptors.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protodebugger.util.ParseGeneratedMessage;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.Message;

public class MessageDescriptor /* extends AbstractFieldDescriptor<Builder<?>> */
implements IMessageDescriptor<Builder<?>> {

	private List<IFieldDescriptor<?>> descriptors;
	private List<IFieldDescriptor<?>> addedDescriptors;
	private GeneratedMessage msg;
	private FieldDescriptor protoField;
	private Builder<?> v;
	private boolean isRepeated;
	private String name;

	public MessageDescriptor(FieldDescriptor protoField) {
		name = protoField.getName();
		this.protoField = protoField;
		isRepeated = protoField.isRepeated();
		descriptors = new ArrayList<IFieldDescriptor<?>>();
		parseFieldDescriptor();
	}

	public MessageDescriptor(GeneratedMessage msg) {
		name = msg.getDescriptorForType().getName();
		this.msg = msg;
		isRepeated = false;
		descriptors = new ArrayList<IFieldDescriptor<?>>();
		parseGenMessage();
	}

	private void parseGenMessage() {
		for (FieldDescriptor childFields : msg.getDescriptorForType()
				.getFields()) {
			descriptors.add(ParseGeneratedMessage
					.parseFieldDescriptor(childFields));
		}
	}

	private void parseFieldDescriptor() {
		for (FieldDescriptor childFields : protoField.getMessageType()
				.getFields()) {
			descriptors.add(ParseGeneratedMessage
					.parseFieldDescriptor(childFields));
		}
	}

	public Message getMessage() {
		Builder<?> msgBuilder = getValue();
		for (IFieldDescriptor<?> descriptor : descriptors) {
			if (!descriptor.buildMsg(msgBuilder)){
				System.err.println(descriptor.getName());
				return null;
			}
		}
		return msgBuilder.build();
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		if (protoField != null) {
			v = (Builder<?>) builder.newBuilderForField(protoField);
			Message builtMsg = getMessage();
			if (builtMsg == null)
				return false;
			if(protoField.isRepeated())
				builder.addRepeatedField(protoField, builtMsg);
			else
				builder.setField(protoField, builtMsg);
			return true;
		} else
			return false;
	}

	@Override
	public List<IFieldDescriptor<?>> getDescriptors() {

		List<IFieldDescriptor<?>> currentDescriptors =
				new ArrayList<IFieldDescriptor<?>>();
		if (descriptors != null)
			currentDescriptors.addAll(descriptors);
		if (addedDescriptors != null)
			currentDescriptors.addAll(addedDescriptors);
		return currentDescriptors;
	}

	@Override
	public List<IFieldDescriptor<?>> getAddedDescriptors() {
		if (addedDescriptors == null)
			return Collections.emptyList();
		return addedDescriptors;
	}

	@Override
	public List<IFieldDescriptor<?>> getRepeatedDescriptors() {
		List<IFieldDescriptor<?>> repeated = new ArrayList<IFieldDescriptor<?>>();
		for(IFieldDescriptor<?> descriptor : descriptors)
			if(descriptor.isRepeated())
				repeated.add(descriptor);
		return repeated;
	}
	
	@Override
	public void addDescriptor(IFieldDescriptor<?> descriptor) {
		if(addedDescriptors == null)
			addedDescriptors = new ArrayList<IFieldDescriptor<?>>();
		this.addedDescriptors.add(descriptor);
	}
	
	
	@Override
	public void removeDescriptor(IFieldDescriptor<?> descriptor) {
		if(addedDescriptors == null)return;
		if (addedDescriptors.contains(descriptor))
			addedDescriptors.remove(descriptor);
	}

	@Override
	public void setValue(Object value) {
		throw new RuntimeException(
				"Uh oh not suppose to call MessageDescriptor setValue...");
	}

	@Override
	public Builder<?> getValue() {
		if (msg != null)
			return (Builder<?>) msg.getDefaultInstanceForType()
					.newBuilderForType();
		else
			return v;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isRepeated() {
		// TODO Auto-generated method stub
		return isRepeated;
	}

	@Override
	public FieldDescriptor getProtoField() {
		// TODO Auto-generated method stub
		return protoField;
	}

}
