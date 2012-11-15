package protodebugger.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import protodebugger.model.ProtoMessageGeneric;
import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.util.GenerateMessageFactory;
import protodebugger.util.Logger;
import protodebugger.util.ProtoEvents;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public enum EditorController {

	INSTANCE;

	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	private ProtoInstance editedMessage;

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

	public void editMessage(ProtoMessage msg, ProtoInstance instance) {

		GeneratedMessage genMsg = GenerateMessageFactory.getGenMsgForString(msg
				.getClassName());
		if (genMsg != null) {
			editedMessage = instance;
			model = new ProtoMessageGeneric(genMsg);
			try {
				genMsg = (GeneratedMessage) genMsg.newBuilderForType()
						.mergeFrom(instance.getMessage()).build();
				Logger.INSTANCE.writeDebug("Message has validated");
				model.getProto().setValue(instance.getMessage());
			} catch (InvalidProtocolBufferException e) {
				Logger.INSTANCE
						.writeDebug("Unable to create proto intance. Using default instead.");

			}
		}

		fireEvent(ProtoEvents.UPDATE_EDITOR, model, null);
	}

}
