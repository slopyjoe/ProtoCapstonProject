import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import protodebugger.cache.CachedProtos;
import protodebugger.model.ProtoMessageModel;
import protodebugger.util.ParseProtoMessage;


public class CacheCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ProtoMessageModel protoMsg = ParseProtoMessage.INSTANCE.getCurrentPM();
				CachedProtos.INSTANCE.addCacheMessage("blah", protoMsg.getGenMsg());
		}
		}).start();
		
		return null;
	}

}
