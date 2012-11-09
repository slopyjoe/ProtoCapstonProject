package protodebugger.model.descriptors.generic;
import com.google.protobuf.GeneratedMessage.Builder;

public interface IFieldDescriptor<V> {

	public V getValue();
	public void setValue(Object value);
	public boolean buildMsg(Builder<?> builder);
	public String getName();
	public boolean isRepeated();
}
