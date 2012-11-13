package protodebugger.model.descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage.Builder;

public interface IFieldDescriptor<V> {

	public V getValue();
	public void setValue(Object value);
	public boolean buildMsg(Builder<?> builder);
	public String getName();
	public boolean isRepeated();
	public FieldDescriptor getProtoField();
}
