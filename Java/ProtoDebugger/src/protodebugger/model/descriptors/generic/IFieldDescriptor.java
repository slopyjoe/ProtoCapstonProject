package protodebugger.model.descriptors.generic;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.GeneratedMessage.Builder;

public interface IFieldDescriptor<V> {

	public V getValue();
	public void setDefaultValue(V v);
	public boolean buildMsg(Builder<?> builder);
	public String getName();
	public Widget getWidget();
	
}
