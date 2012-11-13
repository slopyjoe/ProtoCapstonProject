package protodebugger.model.descriptors;

import java.util.List;

import com.google.protobuf.Message;

public interface IMessageDescriptor<V> extends IFieldDescriptor<V> {

	
	public List<IFieldDescriptor<?>> getDescriptors();
	public List<IFieldDescriptor<?>> getAddedDescriptors();
	public List<IFieldDescriptor<?>> getRepeatedDescriptors();
	public void addDescriptor(IFieldDescriptor<?> descriptor);
	public void removeDescriptor(IFieldDescriptor<?> descriptor);
	public Message getMessage();
	
	
}
