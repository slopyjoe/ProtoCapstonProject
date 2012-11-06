package protodebugger.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.GeneratedMessage;

public class ProtoPackage {

	private String filePath;
	private Map<GeneratedMessage, List<ProtoMessage>> protoMsgs;
	
	public ProtoPackage(String filePath, Map<GeneratedMessage, List<ProtoMessage>> protoMsgs)
	{
		this.filePath = filePath;
		this.protoMsgs = protoMsgs;
	}
	
	public Map<GeneratedMessage, List<ProtoMessage>> getProtoMsgs()
	{
		return protoMsgs;
	}
	
	public void setProtoMsgs(Map<GeneratedMessage, List<ProtoMessage>> protoMsgs)
	{
		if(protoMsgs == null)
		{
			protoMsgs = new HashMap<GeneratedMessage, List<ProtoMessage>>();
		}
		protoMsgs.clear();
		protoMsgs.putAll(protoMsgs);
	}
	
	
	
}
