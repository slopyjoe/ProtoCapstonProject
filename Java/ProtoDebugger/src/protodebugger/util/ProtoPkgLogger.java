package protodebugger.util;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

public class ProtoPkgLogger {

	private static String HEADER = "---------%s----------";
	private static String TAIL = "---------%s----------";
	public static String getTail(int spaces){
		String ret = "-";
		for(int i = 0; i < spaces; i++)
			ret+="-";	
			return ret;
	}
	public static void printProtoPackage(ProtoPackage pkg){
		String pkgHeader = "ProtoPackage " + pkg.getName();
		Logger.INSTANCE.writeInfo(String.format(HEADER, pkgHeader));
		for(ProtoMessage msg : pkg.getMsgsList()){
			printProtoMessage(msg);
		}
		Logger.INSTANCE.writeInfo(String.format(TAIL, getTail(pkgHeader.length())));
	}
	public static void printProtoMessage(ProtoMessage msg){
		String msgHeader = "ProtoMessage " + msg.getName();
		Logger.INSTANCE.writeInfo(String.format(HEADER, msgHeader));
		Logger.INSTANCE.writeInfo("Classname: " + msg.getClassName());
		for(ProtoInstance ins : msg.getMessageList()){
			printProtoInstance(ins, msg.getClassName());
		}
		Logger.INSTANCE.writeInfo(String.format(TAIL, getTail(msgHeader.length())));
	}
	public static void printProtoInstance(ProtoInstance inst, String className){
		StringBuilder builder = new StringBuilder();
		String instHeader = "ProtoInstance " + inst.getName();
		GeneratedMessage genMsg = GenerateMessageFactory.getGenMsgForString(className);
		String message = "Unable to generate class for message";
		if(genMsg != null)
			try {
				message = genMsg.newBuilderForType().mergeFrom(inst.getMessage()).build().toString();
			} catch (InvalidProtocolBufferException e) {
				message = "Invalid proto message";
			}
		
		builder.append(String.format(HEADER, instHeader)+"\n");
		builder.append("Raw Data: " + inst.getMessage().toStringUtf8()+"\n");
		builder.append("Message:\n" + message+"\n");
		builder.append(String.format(TAIL, getTail(instHeader.length())));
		Logger.INSTANCE.writeInfo(builder.toString());
	}
	
}
