package protodebugger.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import protodebugger.model.ProtoMessage;
import protodebugger.util.ProtoEvents;

public class EditorController implements PropertyChangeListener {

	private ProtoMessage editedMessage;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ProtoEvents protoEvents = ProtoEvents.valueOf(evt.getPropertyName());
		switch (protoEvents){
		}
	}

}
