package protodebugger.model.descriptors.generic;

import java.util.List;

public interface IMessageDescriptor<V> extends IFieldDescriptor<V> {

	
	public List<IFieldDescriptor<?>> getDescriptors();
	public void addDescriptor(IFieldDescriptor<?> descriptor);
	public void removeDescriptor(IFieldDescriptor<?> descriptor);
	
}
