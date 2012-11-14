package protodebugger.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protodebugger.model.descriptors.IFieldDescriptor;
import protodebugger.model.descriptors.MessageDescriptor;
import protodebugger.util.ParseGeneratedMessage;
import protodebugger.util.Logger;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;


public class ProtoMessageGeneric {

	private List<IFieldDescriptor<?>> fieldDescriptors;
	private GeneratedMessage genMsg;
	private MessageDescriptor desc;
	public ProtoMessageGeneric(GeneratedMessage msg){
		setGenMsg(msg);
	}
	
	private  void setGenMsg(GeneratedMessage msg){
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
	
	public GeneratedMessage getGenMsg(){
		return genMsg;
	}
	
	public ByteString buildByteStringProto()
	{
		
		Message message = desc.getMessage();
		if(message != null)
		{
		Logger.INSTANCE.writeInfo(message.toString());
		return message.toByteString();
		}else{
			Logger.INSTANCE.writeError("Error");
			return null;
		}
	}
	
}
