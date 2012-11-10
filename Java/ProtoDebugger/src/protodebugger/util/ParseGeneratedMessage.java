package protodebugger.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import protodebugger.model.descriptors.generic.AbstractFieldDescriptor;
import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.MessageDescriptor;
import protodebugger.model.swt.SWTFieldWrapper;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.GeneratedMessage;

public class ParseGeneratedMessage {

	public static List<IFieldDescriptor<?>> parseGeneric(GeneratedMessage msg) {
		List<IFieldDescriptor<?>> list = new ArrayList<IFieldDescriptor<?>>();
		for (Descriptors.FieldDescriptor field : msg.getDescriptorForType()
				.getFields()) {
			list.add(parseFieldDescriptor(field));
		}
		return list;
	}

	public static IFieldDescriptor<?> parseFieldDescriptor(
			Descriptors.FieldDescriptor field) {

		switch (field.getJavaType()) {

		case FLOAT:
			return new AbstractFieldDescriptor<Float>(field) {
				@Override
				public void setValue(Object value) {
					if (value instanceof Float)
						v = (Float) value;
					else if (value instanceof String)
						v = Float.parseFloat(value.toString());
					else
						v = null;
				}
			};
		case INT:
			return new AbstractFieldDescriptor<Integer>(field) {
				@Override
				public void setValue(Object value) {
					if (value instanceof Integer)
						v = (Integer) value;
					else if (value instanceof String)
						v = Integer.parseInt(value.toString());
					else
						v = null;
				}
			};
		case DOUBLE:
			return new AbstractFieldDescriptor<Double>(field) {
				@Override
				public void setValue(Object value) {
					if (value instanceof Double)
						v = (Double) value;
					else if (value instanceof String)
						v = Double.parseDouble(value.toString());
					else
						v = null;
				}
			};
		case LONG:
			return new AbstractFieldDescriptor<Double>(field) {
				@Override
				public void setValue(Object value) {
					if (value instanceof Double)
						v = (Double) value;
					else if (value instanceof String)
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
					if (value instanceof ByteString)
						v = (ByteString) value;
					else if (value instanceof String)
						v = ByteString.copyFrom(value.toString().getBytes());
					else
						v = null;
				}
			};
		case BOOLEAN:

			return new AbstractFieldDescriptor<Boolean>(field) {
				@Override
				public void setValue(Object value) {
					if (value instanceof Boolean)
						v = (Boolean) value;
					else if (value instanceof String)
						v = Boolean.parseBoolean(value.toString());
					else
						v = null;
				}
			};
		case ENUM:
			return new AbstractFieldDescriptor<EnumValueDescriptor>(field) {
				@Override
				public void setValue(Object value) {
					if (value instanceof Integer)
						v = (EnumValueDescriptor) protoField.getEnumType()
								.findValueByNumber((Integer) value);
					else if (value instanceof String)
						v = (EnumValueDescriptor) protoField.getEnumType()
								.findValueByName(value.toString());
				}
			};
		case MESSAGE:
			return new MessageDescriptor(field);
		default:
			return null;
		}
	}

	public static SWTFieldWrapper parseSWT(IFieldDescriptor<?> field,
			Composite parent) {

		switch (field.getProtoField().getJavaType()) {
		case FLOAT:
		case INT:
		case DOUBLE:
		case LONG:
			return new SWTFieldWrapper(new Text(parent, SWT.NONE)) {

				@Override
				public void widgetValueToProtoField(IFieldDescriptor<?> field) {
					if (swtWidget != null) {
						try {
							Integer.valueOf(((Text) swtWidget).getText());
							field.setValue(((Text) swtWidget).getText());
						} catch (NumberFormatException nfe) {
							System.err.println("Incompatible types");
							((Text) swtWidget).setText("");
						}

					}

				}

				@Override
				public void protoValueToWidget(IFieldDescriptor<?> field) {
					if (field.getValue() != null)
						((Text) swtWidget).setText(String.valueOf(field
								.getValue()));
				}
			};
		case STRING:
		case BYTE_STRING:
			return new SWTFieldWrapper(new Text(parent, SWT.NONE)) {

				@Override
				public void widgetValueToProtoField(IFieldDescriptor<?> field) {
					if (swtWidget != null) {
						field.setValue(((Text) swtWidget).getText());
					}

				}

				@Override
				public void protoValueToWidget(IFieldDescriptor<?> field) {
					if (field.getValue() != null)
						((Text) swtWidget).setText(String.valueOf(field
								.getValue()));
				}
			};
		case BOOLEAN:

			return new SWTFieldWrapper(new Button(parent, SWT.CHECK)) {

				@Override
				public void widgetValueToProtoField(IFieldDescriptor<?> field) {
					if (swtWidget != null) {
						field.setValue(((Button) swtWidget).getSelection());

					}

				}

				@Override
				public void protoValueToWidget(IFieldDescriptor<?> field) {
					if (field.getValue() != null)
						((Button) swtWidget).setSelection((Boolean) field
								.getValue());
				}
			};
		case ENUM:
			Combo fieldCombo = new Combo(parent, SWT.NONE);
			int arraySize = field.getProtoField().getEnumType().getValues()
					.size();
			if (field.getProtoField().isOptional())
				arraySize += 1;
			String[] ret = new String[arraySize];
			for (EnumValueDescriptor evd : field.getProtoField().getEnumType()
					.getValues())
				ret[evd.getIndex()] = evd.getName();
			if (field.getProtoField().isOptional())
				ret[arraySize - 1] = "";
			return new SWTFieldWrapper(fieldCombo) {

				@Override
				public void widgetValueToProtoField(IFieldDescriptor<?> field) {
					if (swtWidget != null) {
						field.setValue(((Combo) swtWidget).getSelectionIndex());
					}

				}

				@Override
				public void protoValueToWidget(IFieldDescriptor<?> field) {
					if (field.getValue() != null) {
						EnumValueDescriptor evd = (EnumValueDescriptor) field
								.getValue();
						((Combo) swtWidget).select(evd.getIndex());
					}
				}
			};
		case MESSAGE:
			return new SWTFieldWrapper(new Text(parent, SWT.NONE)) {

				@Override
				public void widgetValueToProtoField(IFieldDescriptor<?> field) {
					}

				@Override
				public void protoValueToWidget(IFieldDescriptor<?> field) {	
				}
			};
		default:
			return null;
		}
	}

}
