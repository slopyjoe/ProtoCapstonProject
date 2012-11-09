package protodebugger.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.GeneratedMessage;

public enum CachedProtos {
	INSTANCE;
	private Map<String, List<GeneratedMessage>> cachedProtos =
			new HashMap<String, List<GeneratedMessage>>();
	private String filePath;
	
	
	public void addCacheMessage(String name, GeneratedMessage msg)
	{
		if(!cachedProtos.containsKey(name))
			cachedProtos.put(name, new ArrayList<GeneratedMessage>());
		cachedProtos.get(name).add(msg);
	}
	
	public final Map<String, List<GeneratedMessage>> getCacheProtos()
	{
		return cachedProtos;
	}
	
	
}
