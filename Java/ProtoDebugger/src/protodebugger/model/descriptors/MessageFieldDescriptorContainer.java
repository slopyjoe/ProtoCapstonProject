<<<<<<< HEAD
package protodebugger.model.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import protodebugger.util.ParseProtoMessage;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class MessageFieldDescriptorContainer extends FieldDescriptorContainer {
	private ArrayList<FieldDescriptorContainer> subMembers;
	private Composite parent;
	public MessageFieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		super(field);
		subMembers = new ArrayList<FieldDescriptorContainer>();
		parseMessage();
	}
	private void parseMessage()
	{
		for(Descriptors.FieldDescriptor inner : field.getMessageType().getFields())
		{
			FieldDescriptorContainer subField = 
					ParseProtoMessage.INSTANCE.parseFieldDescriptor(inner);
			subField.setSubField(true);
			subField.setFieldParent(this);
			subMembers.add(subField);
		}
	}
	public void addMember(FieldDescriptorContainer field, int index)
	{
		subMembers.add(index, field);
	}
	public List<FieldDescriptorContainer> getMembers()
	{
		return subMembers;
	}
	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		Builder<?> innerBuild = (Builder<?>)build.newBuilderForField(field);
		for(FieldDescriptorContainer inner : getMembers())
		{
			System.out.println("Building " + inner.toString());
			inner.buildMsg(innerBuild);
		}
		if(field.isRepeated())
			build.addRepeatedField(field, innerBuild.build());
		else
			build.setField(field, innerBuild.build());
		return true;
	}

	@Override
	public String toString() {
		StringBuilder build = new 
				StringBuilder("MessageField name = " + name +"\n");
		for(FieldDescriptorContainer field : subMembers)
			build.append("\t" + field.toString() + "\n");
		return build.toString();
	}

	public void setParent(Composite parent)
	{
		this.parent = parent;
	}
	@Override
	public Widget getWidget(Composite parent) {
		return null;
	}

	@Override
	public Composite getParent() {
		return parent;
	}

}
=======
package protodebugger.model.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import protodebugger.util.ParseProtoMessage;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage.Builder;

public class MessageFieldDescriptorContainer extends FieldDescriptorContainer {
	private ArrayList<FieldDescriptorContainer> subMembers;
	private Composite parent;
	public MessageFieldDescriptorContainer(Descriptors.FieldDescriptor field)
	{
		super(field);
		subMembers = new ArrayList<FieldDescriptorContainer>();
		parseMessage();
	}
	private void parseMessage()
	{
		for(Descriptors.FieldDescriptor inner : field.getMessageType().getFields())
		{
			FieldDescriptorContainer subField = 
					ParseProtoMessage.INSTANCE.parseFieldDescriptor(inner);
			subField.setSubField(true);
			subField.setFieldParent(this);
			subMembers.add(subField);
		}
	}
	public void addMember(FieldDescriptorContainer field, int index)
	{
		subMembers.add(index, field);
	}
	public List<FieldDescriptorContainer> getMembers()
	{
		return subMembers;
	}
	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public boolean buildMsg(Builder<?> build) {
		Builder<?> innerBuild = (Builder<?>)build.newBuilderForField(field);
		for(FieldDescriptorContainer inner : getMembers())
		{
			System.out.println("Building " + inner.toString());
			inner.buildMsg(innerBuild);
		}
		if(field.isRepeated())
			build.addRepeatedField(field, innerBuild.build());
		else
			build.setField(field, innerBuild.build());
		return true;
	}

	@Override
	public void setValue(Object value) 
	{
	}

	@Override
	public String toString() {
		StringBuilder build = new 
				StringBuilder("MessageField name = " + name +"\n");
		for(FieldDescriptorContainer field : subMembers)
			build.append("\t" + field.toString() + "\n");
		return build.toString();
	}

	public void setParent(Composite parent)
	{
		this.parent = parent;
	}
	@Override
	public Widget getWidget(Composite parent) {
		return null;
	}

	@Override
	public Composite getParent() {
		return parent;
	}

}
>>>>>>> 5a14f9bb2eb2e5da316d0185feeade7969c536bd
