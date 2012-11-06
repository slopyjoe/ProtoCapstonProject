package protodebugger.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import protodebugger.model.ProtoMessage;
import protodebugger.model.descriptors.BooleanFieldDescriptorContainer;
import protodebugger.model.descriptors.EnumFieldDescriptorContainer;
import protodebugger.model.descriptors.FieldDescriptorContainer;
import protodebugger.model.descriptors.MessageFieldDescriptorContainer;
import protodebugger.model.descriptors.NumberFieldDescriptorContainer;
import protodebugger.model.descriptors.TextFieldDescriptorContainer;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.google.protobuf.Message;

public enum ParseProtoMessage {

		INSTANCE;
		
		private Map<GeneratedMessage, ProtoMessage> members=
				new HashMap<GeneratedMessage, ProtoMessage>();
		private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
		private GeneratedMessage current;
		private MessageConsole protoConsole;
		
		private ParseProtoMessage()
		{
			protoConsole = new MessageConsole("PROTO CONSOLE", null);
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{protoConsole});
		}
		private void printInformation(String info)
		{
			System.out.println(info);
			MessageConsoleStream stream = protoConsole.newMessageStream();
			stream.println(info);
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private void printError(String error)
		{
			System.err.println(error);
		}
		public void parse(GeneratedMessage msg)
		{
			printInformation("\t Loading Message");
			ArrayList<FieldDescriptorContainer> fields = 
					new ArrayList<FieldDescriptorContainer>();
			ArrayList<FieldDescriptorContainer> repeated = 
					new ArrayList<FieldDescriptorContainer>();
			FieldDescriptorContainer container;
			for(Descriptors.FieldDescriptor field : msg.getDescriptorForType().getFields())
			{
				container = parseFieldDescriptor(field);
				if(container != null)
				{
					fields.add(container);
					if(field.isRepeated())
					{
						repeated.add(container);
					}
				}
			}
			members.put(msg,  new ProtoMessage(msg, fields, repeated));
			printContainments();
		}
		public void addChangeListener(PropertyChangeListener pcl)
		{
			pcs.addPropertyChangeListener(pcl);
		}
		public void selectionChange(GeneratedMessage msg)
		{
			current = msg;
			pcs.firePropertyChange("PROTO_CHANGE", null, msg);
		}
		public FieldDescriptorContainer parseFieldDescriptor(Descriptors.FieldDescriptor field)
		{
			switch(field.getJavaType())
			{
			case FLOAT:
			case INT:
			case DOUBLE:
			case LONG:
				return (new NumberFieldDescriptorContainer(field));
			case STRING:
			case BYTE_STRING:
				return (new TextFieldDescriptorContainer(field));
			case BOOLEAN:
				return (new BooleanFieldDescriptorContainer(field));
			case ENUM:
				return (new EnumFieldDescriptorContainer(field));
			case MESSAGE:
				return (new MessageFieldDescriptorContainer(field));
			default:
				return null;
			}
		}
		public void sendProtoToConsole()
		{
			printInformation("Sending Proto - " + current.getDescriptorForType().getName());
			Builder<?> type = (Builder<?>)current.newBuilderForType();
			for(FieldDescriptorContainer field : getListforMsg(current))
			{
				field.buildMsg(type);
			}
			Message genMsg = type.build();
			printInformation(genMsg.toString() + "\n" + genMsg.toByteString() + "\nMessage Sent\n");
			
		}
		public void removeAddedRepeatedField(FieldDescriptorContainer field)
		{
			removeAddedField(field, current);
		}
		public void removeAddedField(FieldDescriptorContainer field, GeneratedMessage msg)
		{
			if(members.containsKey(msg))
			{
				members.get(msg).removeAddedField(field);
				pcs.firePropertyChange("REMOVE_FIELD", field, msg);
			}
		}
		public void addRepeatedField(FieldDescriptorContainer field)
		{
			addRepeatedField(field, current);
		}
		public void addRepeatedField(FieldDescriptorContainer field, GeneratedMessage msg)
		{
			FieldDescriptorContainer added = parseFieldDescriptor(field.getFieldDescriptor());
			if(!field.isSubField() && members.containsKey(msg))
			{
				int index = members.get(msg).getContents().indexOf(field);
				members.get(msg).getContents().add(index+1, added);
			}
			else
			{
				FieldDescriptorContainer parentField = field.getFieldParent();
				if(parentField != null && parentField instanceof MessageFieldDescriptorContainer)
				{
					int index = ((MessageFieldDescriptorContainer)parentField).getMembers().indexOf(field);
					((MessageFieldDescriptorContainer)parentField).addMember(added, index+1);
				}
			}
			members.get(msg).addAddField(field);
			pcs.firePropertyChange("REPEATED_FIELD", field, added);
		}
		
		public ProtoMessage getCurrentPM()
		{
			return members.get(current);
		}
		public List<FieldDescriptorContainer> getListforMsg(GeneratedMessage msg)
		{
			printInformation("Retrieving contents for '" + msg.getDescriptorForType().getName() +"'");
			if(!members.containsKey(msg))
				parse(msg);
			return members.get(msg).getContents();
		}
		public List<FieldDescriptorContainer> getRepeatedforMsg()
		{
			if(!members.containsKey(current))
				return new ArrayList<FieldDescriptorContainer>();
			return members.get(current).getRepeatedFields();
		}
		public List<FieldDescriptorContainer> getAddedforMsg()
		{
			if(!members.containsKey(current))
				return new ArrayList<FieldDescriptorContainer>();
			return members.get(current).getAddFields();
		}
		public void printContainments()
		{
			for(GeneratedMessage msg: members.keySet())
			{
				System.out.println("New Message " + msg.toString());
				for(FieldDescriptorContainer field: members.get(msg).getContents())
					System.out.println(field.toString());
			}
		}
		
		/*public static void main(String [] args)
		{
			ParseProtoMessage.INSTANCE.parse(Simple.SimpleExample.getDefaultInstance());
			ParseProtoMessage.INSTANCE.printContainments();
		}*/
}
