package protodebugger.model.descriptors.generic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage.Builder;

public class RepeatedFieldDescriptor<V> extends FieldDescriptorContainer<V> {

	public RepeatedFieldDescriptor(Descriptors.FieldDescriptor protoField) {
		super(protoField);
	}

	@Override
	public boolean buildMsg(Builder<?> builder) {

		if (v == null) {
			return false;
		} else {
			builder.addRepeatedField(protoField, v);
			return true;
		}
	}
	
	
	//Known subclasses for java types needed by protoFirl
	/**
	 * 
	 * @author sloppyjoe
	 *
	 */
	public class FloatRepeatedFieldDescriptor extends RepeatedFieldDescriptor<Float>{

		public FloatRepeatedFieldDescriptor(FieldDescriptor protoField) {
			super(protoField);
		}
		
		public Widget getWidget()
		{
			Float f = v;
			Text text = new Text(null, SWT.NONE);
			text.setText(v.toString());
			return text;
		}
		
	}
	public class BooleanReapeated extends RepeatedFieldDescriptor<Boolean>{

		public BooleanReapeated(FieldDescriptor protoField) {
			super(protoField);
		}
		
		public Widget getWidget(){
			Button butt = new Button(null, SWT.CHECK);
			butt.setEnabled(v);
			return butt;
		}
		
	}
	public class ByteStringRepeatedField extends RepeatedFieldDescriptor<ByteString>
	{

		public ByteStringRepeatedField(FieldDescriptor protoField) {
			super(protoField);
		}
		
	}

}

