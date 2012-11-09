package protodebugger.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.util.ParseProtoMessage;
import protodebugger.util.ProtoEvents;

import com.google.protobuf.GeneratedMessage;

public enum EditorController implements PropertyChangeListener {

	INSTANCE;
	 private final String DEFAULT_METHOD = "getDefaultInstance";
	

	private ProtoInstance editedMessage;
	private Map<String, GeneratedMessage> generatedClasses = 
			new HashMap<String, GeneratedMessage>();
	
	
	private GeneratedMessage getGenMsgForString(String className)
 {
		try {
			Class<?> genMsg = Class.forName(className);
			Object obj = genMsg.getMethod(DEFAULT_METHOD, null).invoke(
					genMsg, null);
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
	
	public void editMessage(ProtoMessage msg, ProtoInstance instance)
	{
		if(!generatedClasses.containsKey(msg.getClassName()))
		{
			GeneratedMessage genMsg = getGenMsgForString(msg.getClassName());
			generatedClasses.put(msg.getClassName(), genMsg);
		}
		ParseProtoMessage.INSTANCE.selectionChange(generatedClasses.get(msg.getClassName()));
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ProtoEvents protoEvents = ProtoEvents.valueOf(evt.getPropertyName());
		switch (protoEvents){
		}
	}
	


}
