package protodebugger.model.descriptors.generic;

import java.util.List;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;

public class ProtoDescriptor implements IMessageDescriptor<GeneratedMessage>{

	@Override
	public GeneratedMessage getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRepeated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FieldDescriptor getProtoField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IFieldDescriptor<?>> getDescriptors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDescriptor(IFieldDescriptor<?> descriptor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDescriptor(IFieldDescriptor<?> descriptor) {
		// TODO Auto-generated method stub
		
	}

	

	
	
}
