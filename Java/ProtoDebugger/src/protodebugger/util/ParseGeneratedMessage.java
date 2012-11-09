package protodebugger.util;

import java.util.ArrayList;
import java.util.List;

import protodebugger.model.descriptors.generic.AbstractFieldDescriptor;
import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.MessageDescriptor;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.GeneratedMessage;

public class ParseGeneratedMessage {

	public static List<IFieldDescriptor<?>> parseGeneric(GeneratedMessage msg)
	{
		List<IFieldDescriptor<?>> list = new ArrayList<IFieldDescriptor<?>>();
		for(Descriptors.FieldDescriptor field : msg.getDescriptorForType().getFields())
		{
			list.add(parseFieldDescriptor(field));
		}
		return list;
	}
	
	
	
	public static IFieldDescriptor<?> parseFieldDescriptor(Descriptors.FieldDescriptor field){
		
		switch(field.getJavaType())
		{
		case FLOAT:
			return new AbstractFieldDescriptor<Float>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof Float)
						v = (Float) value;
					else if(value instanceof String)
						v = Float.parseFloat(value.toString());
					else 
						v = null;
				}
			};
		case INT:
			return new AbstractFieldDescriptor<Integer>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof Integer)
						v = (Integer) value;
					else if(value instanceof String)
						v = Integer.parseInt(value.toString());
					else 
						v = null;
				}
			};
		case DOUBLE:
			return new AbstractFieldDescriptor<Double>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof Double)
						v = (Double) value;
					else if(value instanceof String)
						v = Double.parseDouble(value.toString());
					else 
						v = null;
				}
			};
		case LONG:
			return new AbstractFieldDescriptor<Double>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof Double)
						v = (Double) value;
					else if(value instanceof String)
						v = Double.parseDouble(value.toString());
					else 
						v = null;
				}
			};
		case STRING:
			return new AbstractFieldDescriptor<String>(field) {
				@Override
				public void setValue(Object value) {
					v = value.toString();
				}
			};
		case BYTE_STRING:
			return new AbstractFieldDescriptor<ByteString>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof ByteString)
						v = (ByteString) value;
					else if(value instanceof String)
						v =ByteString.copyFrom(value.toString().getBytes());
					else 
						v = null;
				}
			};
		case BOOLEAN:
			
			return new AbstractFieldDescriptor<Boolean>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof Boolean)
						v = (Boolean) value;
					else if(value instanceof String)
						v = Boolean.parseBoolean(value.toString());
					else 
						v = null;
				}
			};
		case ENUM:
			return new AbstractFieldDescriptor<EnumValueDescriptor>(field) {
				@Override
				public void setValue(Object value) {
					if(value instanceof Integer)
						v = (EnumValueDescriptor) protoField.getEnumType().findValueByNumber((Integer)value);
					else if(value instanceof String)
						v = (EnumValueDescriptor) protoField.getEnumType().findValueByName(value.toString());
				}
			};
		case MESSAGE:
			return new MessageDescriptor(field);
		default:
			return null;
		}
	}
	
}
