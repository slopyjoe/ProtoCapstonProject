package protodebugger.util;

import java.util.ArrayList;

import protodebugger.model.ProtoMessage;
import protodebugger.model.descriptors.BooleanFieldDescriptorContainer;
import protodebugger.model.descriptors.EnumFieldDescriptorContainer;
import protodebugger.model.descriptors.FieldDescriptorContainer;
import protodebugger.model.descriptors.MessageFieldDescriptorContainer;
import protodebugger.model.descriptors.NumberFieldDescriptorContainer;
import protodebugger.model.descriptors.TextFieldDescriptorContainer;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage;

public class ParseGeneratedMessage {

	public static ProtoMessage parseGeneratedMessage(GeneratedMessage msg){
		ArrayList<FieldDescriptorContainer> fields = 
				new ArrayList<FieldDescriptorContainer>();
		ArrayList<FieldDescriptorContainer> repeated = 
				new ArrayList<FieldDescriptorContainer>();
		FieldDescriptorContainer container;
		for(Descriptors.FieldDescriptor field : msg.getDescriptorForType().getFields())
		{
			container = parseFieldDescriptor(field);
			if(container != null)
			{
				fields.add(container);
				if(field.isRepeated())
				{
					repeated.add(container);
				}
			}
		}
		
		return new ProtoMessage(msg, fields, repeated);
	}
	
	public static FieldDescriptorContainer parseFieldDescriptor(Descriptors.FieldDescriptor field){

		switch(field.getJavaType())
		{
		case FLOAT:
		case INT:
		case DOUBLE:
		case LONG:
			return (new NumberFieldDescriptorContainer(field));
		case STRING:
		case BYTE_STRING:
			return (new TextFieldDescriptorContainer(field));
		case BOOLEAN:
			return (new BooleanFieldDescriptorContainer(field));
		case ENUM:
			return (new EnumFieldDescriptorContainer(field));
		case MESSAGE:
			return (new MessageFieldDescriptorContainer(field));
		default:
			return null;
		}
	}
	
}
