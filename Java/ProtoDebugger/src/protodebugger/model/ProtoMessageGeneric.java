package protodebugger.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.util.ParseGeneratedMessage;

import com.google.protobuf.GeneratedMessage;


public class ProtoMessageGeneric {

	private List<IFieldDescriptor<?>> fieldDescriptors;
	private GeneratedMessage genMsg;

	public ProtoMessageGeneric(){
		
	}
	
	public void setGenMsg(GeneratedMessage msg){
		this.genMsg = msg;
		fieldDescriptors = new ArrayList<IFieldDescriptor<?>>(ParseGeneratedMessage.parseGeneric(genMsg));
	}
	
	public List<IFieldDescriptor<?>> getDescriptors(){
		if(fieldDescriptors == null)
			return Collections.emptyList();
		return fieldDescriptors;
	}
}
