package protodebugger.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.MessageDescriptor;
import protodebugger.util.ParseGeneratedMessage;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;


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
	

	
	public ByteString buildByteStringProto()
	{
		
		Message message = desc.getMessage();
		if(message != null)
		{
		System.out.println(message.toString());
		return message.toByteString();
		}else{
			System.err.println("Error");
			return null;
		}
	}
	
}
