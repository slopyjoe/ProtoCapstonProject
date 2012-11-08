package protodebugger.controller;

import com.google.protobuf.ByteString;

import protodebugger.model.ProtoPackageModel;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

public enum ViewerController {

	INSTANCE;
	private ProtoPackageModel packageModel  = new ProtoPackageModel();
	
	private ViewerController()
	{
		ProtoPackage.Builder pkgBuild = ProtoPackage.newBuilder();
		ProtoMessage.Builder msgBuild = ProtoMessage.newBuilder();
		pkgBuild.setName("Hello");
		pkgBuild.setFilePath("filePath");
		msgBuild.setName("Message");
		msgBuild.addClassName("class name 1");
		msgBuild.addClassName("class name 2");
		msgBuild.addMessage(ByteString.copyFrom("adf".getBytes()));
		pkgBuild.addMsgs(msgBuild);
		packageModel.addProtoPkg(pkgBuild.build());
	}
	
	public ProtoPackageModel getModel()
	{
		return packageModel;
	}
	
}
