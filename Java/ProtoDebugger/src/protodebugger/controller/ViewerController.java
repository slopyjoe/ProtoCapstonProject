package protodebugger.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;

import protodebugger.model.ProtoPackageModel;
import protodebugger.model.ProtoMessageModel;
import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.views.ProtoCacheViewer;
import protodebugger.util.ProtoEvents;

import com.google.protobuf.GeneratedMessage;

/**
 * 
 * ViewerController
 * DESCRIPTION: {@link ProtoCacheViewer} controller.
 * Keeps {@link ProtoCacheViewer} in sync with {@link ProtoPackageModel}
 * 
 * PACKAGE: protodebugger.controller
 * @author sloppyjoe -Initial release
 *
 */
public enum ViewerController {

	INSTANCE;
	private final String DEFAULT_METHOD = "getDefaultInstance";
	private ProtoPackageModel packageModel  = new ProtoPackageModel();
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private ProtoInstance viewedMessage;
	private ViewerController()
	{
	}
	
	/**
	 * Returns the current model in control
	 * 
	 * @return {@link ProtoPackageModel}
	 */
	public final ProtoPackageModel getModel()
	{
		return packageModel;
	}
	
	public void addProtoPkg(ProtoPackage pkg)
	{
		packageModel.addProtoPkg(pkg);
		pcs.firePropertyChange(ProtoEvents.CACHED_LOADED.name(), null, this.getModel() );		
	}
	
	private GeneratedMessage getGenMsgForString(String className) {
		try {
			Class<?> genMsg = Class.forName(className);
			Object obj = genMsg.getMethod(DEFAULT_METHOD, null).invoke(genMsg,
					null);
			if (obj instanceof GeneratedMessage) {
				return (GeneratedMessage) obj;
			}

		} catch (ClassNotFoundException | NoSuchMethodException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException cnfe) {
			cnfe.printStackTrace();
		}
		return null;
	}
	
	public void updatePackageModel(ProtoPackage pkg, GeneratedMessage msg, String name)
	{
		try
		{
			ProtoInstance.Builder builder = ProtoInstance.newBuilder();
			builder.setName(name);
			builder.setMessage(msg.toByteString());
			
			for (ProtoMessage var : pkg.getMsgsList())
			{
				if ( this.getGenMsgForString(var.getClassName()).equals(msg) )
				{
					ProtoMessage.Builder proto = ProtoMessage.newBuilder(var);
					proto.addMessage(builder.build());
				}	
				else
				{
					ProtoMessage.Builder newProto = ProtoMessage.newBuilder();
					newProto.addMessage(builder.build());
					pkg.getMsgsList().add(newProto.build());
				}
			}
			pcs.firePropertyChange(ProtoEvents.CACHED_LOADED.name(), null, this.getModel() );	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void addChangeListener(PropertyChangeListener pcl)
	{
		pcs.addPropertyChangeListener(pcl);
	}
	
}
