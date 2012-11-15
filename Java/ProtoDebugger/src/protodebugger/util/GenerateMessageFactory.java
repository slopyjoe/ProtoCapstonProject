package protodebugger.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.GeneratedMessage;

public class GenerateMessageFactory {
	private static final String DEFAULT_METHOD = "getDefaultInstance";
	private static Map<String, GeneratedMessage> generatedClasses = new HashMap<String, GeneratedMessage>();
	
	
	
	
	public static GeneratedMessage getGenMsgForString(String className) {
		if(generatedClasses.containsKey(className))
			return generatedClasses.get(className);
		try {
			Class<?> genMsg = Class.forName(className);
			Object obj = genMsg.getMethod(DEFAULT_METHOD, null).invoke(genMsg,
					null);
			if (obj instanceof GeneratedMessage) {
				generatedClasses.put(className, (GeneratedMessage)obj);
				return (GeneratedMessage) obj;
			}

		} catch (ClassNotFoundException | NoSuchMethodException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException cnfe) {
			Logger.INSTANCE.writeError(cnfe.getMessage());
		}
		return null;
	}
	
}
