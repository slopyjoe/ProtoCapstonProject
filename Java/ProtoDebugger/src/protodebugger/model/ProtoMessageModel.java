package protodebugger.model;

import java.util.ArrayList;
import java.util.List;

import protodebugger.model.descriptors.FieldDescriptorContainer;
import protodebugger.util.ParseProtoMessage;

import com.google.protobuf.GeneratedMessage;


public class ProtoMessageModel {
	private GeneratedMessage genMsg;
	private List<FieldDescriptorContainer> contents;
	private List<FieldDescriptorContainer> repeatedFields;
	private List<FieldDescriptorContainer> addedFields;
	
	
	public ProtoMessageModel(GeneratedMessage genMsg, 
						List<FieldDescriptorContainer> contents,
						List<FieldDescriptorContainer> repeatedFields)
	{
		super();
		this.genMsg = genMsg;
		this.contents = contents;
		this.repeatedFields = repeatedFields;
	}

	public GeneratedMessage getGenMsg() {
		return genMsg;
	}

	public void setGenMsg(GeneratedMessage genMsg) {
		this.genMsg = genMsg;
	}

	public List<FieldDescriptorContainer> getContents() {
		return contents;
	}

	public void setContents(List<FieldDescriptorContainer> contents) {
		this.contents = contents;
	}

	public List<FieldDescriptorContainer> getRepeatedFields() {
		return repeatedFields;
	}
	
	public void addAddField(FieldDescriptorContainer add)
	{
		if(addedFields == null)
			addedFields = new ArrayList<FieldDescriptorContainer>();
		addedFields.add(add);
	}
	
	public void removeAddedField(FieldDescriptorContainer add)
	{
		if(addedFields == null)
			return;
		addedFields.remove(add);
	}
	
	public List<FieldDescriptorContainer> getAddFields()
	{
		return addedFields;
	}
	
	public static ProtoMessageModel parseGeneratedMsg(GeneratedMessage msg)
	{
		return ParseProtoMessage.INSTANCE.parse(msg);
		
	}
	
}