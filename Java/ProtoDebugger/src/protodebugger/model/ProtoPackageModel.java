package protodebugger.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.views.ProtoCacheViewer;

/**
 * 
 * ProtoPackageModel
 * DESCRIPTION: Model for {@link ProtoCacheViewer} Tree's View.
 * Each {@link ProtoPackage} is one file representing an eclipse like
 * package.  The ProtoPackage contains the file path and a list of
 * generated messages.
 * 
 * TODO:	
 * 	- Cache the protoPackages file path so the {@link ProtoCacheViewer}
 * can have multiple workspaces.
 * 
 * PACKAGE: protodebugger.model
 * @author sloppyjoe -Initial release
 *
 */
public class ProtoPackageModel {

	private Set<ProtoPackage> protoPackages = new HashSet<ProtoPackage>();
	
	public ProtoPackageModel()
	{
	}
	
	/**
	 * Add a {@link ProtoPackage} to the model.
	 * @param addPackage - {@link ProtoPackage}s
	 */
	public void addProtoPkg(ProtoPackage addPackage)
	{
		protoPackages.add(addPackage);
	}

	public void updateProtoPkg(ProtoPackage addPackage)
	{
		if(protoPackages.contains(addPackage)){
			System.out.println("Contains the proto file");
		}
	}
	public void removeProtoPk(ProtoPackage removePkg){
		protoPackages.remove(removePkg);
	}
	
	/**
	 * 
	 * Get the current {@link ProtoPackage}s in model.
	 * 
	 * @return {@link List} of {@link ProtoPackage}
	 */
	public Set<ProtoPackage> getPackages()
	{
		return protoPackages;
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public Object getParent(Object obj)
	{
		if(obj instanceof ProtoMessage)
			return getPackageForMessage((ProtoMessage)obj);
		else if(obj instanceof ProtoInstance)
			return getMessageForInstance((ProtoInstance)obj);
		else
			return null;
	}
	
	public ProtoPackage getPackageForMessage(ProtoMessage msg)
	{
		for(ProtoPackage pkg : protoPackages)
		{
			for(ProtoMessage pkgMsg : pkg.getMsgsList())
			{
				if(pkgMsg.equals(msg))
					return pkg;
			}
		}
		return null;
	}
	public ProtoMessage getMessageForInstance(ProtoInstance instance)
	{
		for(ProtoPackage pkg : protoPackages)
		{
			for(ProtoMessage pkgMsg : pkg.getMsgsList())
			{
				for(ProtoInstance msgIns : pkgMsg.getMessageList())
				{
					if(msgIns.equals(instance))
						return pkgMsg;
				}
			}
		}
		return null;
	}
	public ProtoInstance getInstanceFromString(String name)
	{
		for(ProtoPackage pkg : protoPackages)
		{
			for(ProtoMessage pkgMsg : pkg.getMsgsList())
			{
				for(ProtoInstance msgIns : pkgMsg.getMessageList())
				{
					if(msgIns.getName().equals(name))
						return msgIns;
				}
			}
		}
		return null;
	}
	
	
}
