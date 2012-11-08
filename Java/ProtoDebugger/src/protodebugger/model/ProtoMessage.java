package protodebugger.model;

import java.util.ArrayList;
import java.util.List;

import protodebugger.model.descriptors.FieldDescriptorContainer;

import com.google.protobuf.GeneratedMessage;


public class ProtoMessage {
	private GeneratedMessage genMsg;
	private List<FieldDescriptorContainer> contents;
	private List<FieldDescriptorContainer> repeatedFields;
	private List<FieldDescriptorContainer> addFields;
	
	@Deprecated
	public ProtoMessage(GeneratedMessage genMsg, 
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

	public void setRepeatedFields(List<FieldDescriptorContainer> repeatedFields) {
		this.repeatedFields = repeatedFields;
	}
	
	public void addAddField(FieldDescriptorContainer add)
	{
		if(addFields == null)
			addFields = new ArrayList<FieldDescriptorContainer>();
		addFields.add(add);
	}
	
	public void removeAddedField(FieldDescriptorContainer add)
	{
		if(addFields == null)
			return;
		addFields.remove(add);
	}
	
	public List<FieldDescriptorContainer> getAddFields()
	{
		return addFields;
	}
	
}