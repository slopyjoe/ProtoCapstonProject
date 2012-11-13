package protodebugger.model.descriptors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protodebugger.util.ParseGeneratedMessage;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
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
		parseFieldDescriptors(protoField.getMessageType().getFields());
	}

	public MessageDescriptor(GeneratedMessage msg) {
		name = msg.getDescriptorForType().getName();
		this.msg = msg;
		isRepeated = false;
		descriptors = new ArrayList<IFieldDescriptor<?>>();
		parseFieldDescriptors(msg.getDescriptorForType().getFields());

	}

	private void parseFieldDescriptors(List<FieldDescriptor> msgDescriptors) {
		for (FieldDescriptor msgDescriptor : msgDescriptors) {
			descriptors.add(ParseGeneratedMessage
					.parseFieldDescriptor(msgDescriptor));
		}
	}

	public Message getMessage() {
		Builder<?> msgBuilder = getValue();
		for (IFieldDescriptor<?> descriptor : getDescriptors()) {
			if (!descriptor.buildMsg(msgBuilder)) {
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
			if (protoField.isRepeated())
				builder.addRepeatedField(protoField, builtMsg);
			else
				builder.setField(protoField, builtMsg);
			return true;
		} else
			return false;
	}

	@Override
	public List<IFieldDescriptor<?>> getDescriptors() {

		List<IFieldDescriptor<?>> currentDescriptors = new ArrayList<IFieldDescriptor<?>>();
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
		for (IFieldDescriptor<?> descriptor : descriptors)
			if (descriptor.isRepeated())
				repeated.add(descriptor);
		return repeated;
	}

	@Override
	public void addDescriptor(IFieldDescriptor<?> descriptor) {
		if (addedDescriptors == null)
			addedDescriptors = new ArrayList<IFieldDescriptor<?>>();
		this.addedDescriptors.add(descriptor);
	}

	@Override
	public void removeDescriptor(IFieldDescriptor<?> descriptor) {
		if (addedDescriptors == null)
			return;
		if (addedDescriptors.contains(descriptor))
			addedDescriptors.remove(descriptor);
	}

	@Override
	public void setValue(Object value) {
		if (msg != null && value instanceof ByteString) {
			setValue((ByteString) value);
		} else if (value instanceof Builder<?>) {
			setValue((Builder<?>) value);
		}else if (value instanceof GeneratedMessage)
		{
			Builder<?> builder = (Builder<?>)((GeneratedMessage)value).toBuilder();
			setValue(builder);
		}else{
			System.out.println("unknown type");
		}
	}

	private void setFieldDescriptor(IFieldDescriptor<?> field,
			Builder<?> builder, int index) {
		/*if (field instanceof MessageDescriptor) {
			field.setValue(builder);
		} *//*else*/ {
			if (field.isRepeated())
				field.setValue(builder.getRepeatedField(field.getProtoField(),
						index));
			else
				field.setValue(builder.getField(field.getProtoField()));
		}
	}

	private void setFieldDescriptor(IFieldDescriptor<?> currentField,
			Builder<?> builder) {
		if (currentField.isRepeated()) {
			for (int i = 0; i < builder.getRepeatedFieldCount(currentField
					.getProtoField()); i++) {
				if (i == 0) {
					currentField.setValue(builder.getRepeatedField(
							currentField.getProtoField(), i));
					setFieldDescriptor(currentField, builder, i);
				} else {
					IFieldDescriptor<?> repeatedField = ParseGeneratedMessage
							.parseFieldDescriptor(currentField.getProtoField());
					setFieldDescriptor(repeatedField, builder, i);
					addedDescriptors.add(repeatedField);
				}
			}
		} else {
			setFieldDescriptor(currentField, builder, 0);
		}
	}

	private boolean setValue(Builder<?> builder) {
		addedDescriptors = new ArrayList<IFieldDescriptor<?>>();
		for (IFieldDescriptor<?> currentField : descriptors) {
			if (currentField instanceof MessageDescriptor) {
				setFieldDescriptor(currentField, builder);
			} else if (builder.hasField(currentField.getProtoField())) {
				setFieldDescriptor(currentField, builder);
			}
		}
		return false;
	}

	private boolean setValue(ByteString builtMsg) {
		try {
			Builder<?> builder = (Builder<?>) msg.newBuilderForType()
					.mergeFrom(builtMsg);
			return setValue(builder);

		} catch (InvalidProtocolBufferException e) {
			System.err.println(e.getMessage());
			return false;
		}

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
		return name;
	}

	@Override
	public boolean isRepeated() {
		return isRepeated;
	}

	@Override
	public FieldDescriptor getProtoField() {
		return protoField;
	}

}
