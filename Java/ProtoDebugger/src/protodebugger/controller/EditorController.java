package protodebugger.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import protodebugger.model.ProtoMessageGeneric;
import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.util.ProtoEvents;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public enum EditorController {

	INSTANCE;
	private final String DEFAULT_METHOD = "getDefaultInstance";
	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	private ProtoInstance editedMessage;
	private Map<String, GeneratedMessage> generatedClasses = new HashMap<String, GeneratedMessage>();
	private ProtoMessageGeneric model;

	public void addPropertyListener(PropertyChangeListener list) {
		support.addPropertyChangeListener(list);
	}

	public void fireEvent(ProtoEvents event, Object newVal, Object oldVal) {
		support.firePropertyChange(event.name(), oldVal, newVal);
	}

	public void setModel(ProtoMessageGeneric model) {
		this.model = model;
	}

	public void saveCurrent() {
		ProtoInstance instance = ProtoInstance.newBuilder(editedMessage)
				.setMessage(model.buildByteStringProto()).build();
		ViewerController.INSTANCE.updatePackageModel(instance,
				model.getGenMsg());
	}

	public void buildMsg() {
		if (model != null)
			model.buildByteStringProto();
	}

	private GeneratedMessage getGenMsgForString(String className) {
		try {
			Class<?> genMsg = Class.forName(className);
			Object obj = genMsg.getMethod(DEFAULT_METHOD, null).invoke(genMsg,
					null);
			if (obj instanceof GeneratedMessage) {
				return (GeneratedMessage) obj;
			}

		} catch (ClassNotFoundException | NoSuchMethodException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException cnfe) {
			cnfe.printStackTrace();
		}
		return null;
	}

	public void editMessage(ProtoMessage msg, ProtoInstance instance) {
		if (!generatedClasses.containsKey(msg.getClassName())) {
			GeneratedMessage genMsg = getGenMsgForString(msg.getClassName());
			generatedClasses.put(msg.getClassName(), genMsg);
		}
		editedMessage = instance;
		GeneratedMessage genMsg = generatedClasses.get(msg.getClassName());
		model = new ProtoMessageGeneric(generatedClasses.get(msg.getClassName()));
		try {
			genMsg = (GeneratedMessage) genMsg.newBuilderForType()
					.mergeFrom(instance.getMessage()).build();
			System.out.println("Message has validated");
			model.getProto().setValue(instance.getMessage());
		} catch (InvalidProtocolBufferException e) {
			System.err.println("Unable to create proto intance. Using default instead.");
			
		}

		
		
		fireEvent(ProtoEvents.UPDATE_EDITOR, model, null);
	}

}
