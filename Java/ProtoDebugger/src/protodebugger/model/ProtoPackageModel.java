package protodebugger.model;

import java.util.ArrayList;
import java.util.List;

import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

public class ProtoPackageModel {

	private List<ProtoPackage> protoPackages = new ArrayList<ProtoPackage>();
	
	public ProtoPackageModel()
	{
	}
	
	public void addProtoPkg(ProtoPackage addPackage)
	{
		protoPackages.add(addPackage);
	}
	
	public List<ProtoPackage> getPackages()
	{
		return protoPackages;
	}
	
	
	
	
}
