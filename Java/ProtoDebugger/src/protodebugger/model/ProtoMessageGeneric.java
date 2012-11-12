package protodebugger.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.MessageDescriptor;
import protodebugger.util.ParseGeneratedMessage;

import com.google.protobuf.GeneratedMessage;


public class ProtoMessageGeneric {

	private List<IFieldDescriptor<?>> fieldDescriptors;
	private GeneratedMessage genMsg;
	private MessageDescriptor desc;
	public ProtoMessageGeneric(){
		
	}
	
	public void setGenMsg(GeneratedMessage msg){
		this.genMsg = msg;
		desc = new MessageDescriptor(genMsg);
		fieldDescriptors = new ArrayList<IFieldDescriptor<?>>(ParseGeneratedMessage.parseGeneric(genMsg));
	}
	
	
	public MessageDescriptor getProto(){
		return desc;
	}
	public List<IFieldDescriptor<?>> getDescriptors(){
		if(fieldDescriptors == null)
			return Collections.emptyList();
		return fieldDescriptors;
	}
}
