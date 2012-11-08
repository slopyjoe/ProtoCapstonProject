package protodebugger.model;

import java.util.ArrayList;
import java.util.List;

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

	private List<ProtoPackage> protoPackages = new ArrayList<ProtoPackage>();
	
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
	
	/**
	 * 
	 * Get the current {@link ProtoPackage}s in model.
	 * 
	 * @return {@link List} of {@link ProtoPackage}
	 */
	public List<ProtoPackage> getPackages()
	{
		return protoPackages;
	}
	
	
	
	
}
