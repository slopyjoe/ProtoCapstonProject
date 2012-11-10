package protodebugger.test.packages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.test.AlienSpeciesProto.Alien;
import org.test.AlienSpeciesProto.Alien.Language;
import org.test.AlienSpeciesProto.Alien.PlanetType;

import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;

public class ProtoPackages {


	public static List<ProtoPackage> getPackages()
	
	{
		List<ProtoPackage> packages = new
				ArrayList<ProtoPackage>();
		ProtoPackage.Builder pkgBuild = ProtoPackage.newBuilder();
		ProtoMessage.Builder msgBuild = ProtoMessage.newBuilder();
		ProtoInstance.Builder insBuild = ProtoInstance.newBuilder();
		Alien.Builder alienBld = Alien.newBuilder();
		
		Descriptors.FieldDescriptor field = null;
		for(FieldDescriptor desc : Language.getDescriptor().getFields())
		{
			if(desc.getJavaType() == JavaType.ENUM)
				field = desc;
		}
		pkgBuild.setName("Alian's Protos");
		pkgBuild.setFilePath("/e/temp/alian.protoPkg");
		msgBuild.setName("Alien");
		msgBuild.setClassName(Alien.class.getName());
		insBuild.setName("MarsBar");
		alienBld.setName("MarsBar");
		alienBld.setId(23);
		alienBld.setSpecies("Martian");
		
		alienBld.addTongue(
				Language.newBuilder().setGrammer("Frasier").setField(field, field.getDefaultValue()));
		insBuild.setMessage(alienBld.build().toByteString());
		msgBuild.addMessage(insBuild.build());
		pkgBuild.addMsgs(msgBuild);
		packages.add(pkgBuild.build());
		
		
		
		pkgBuild = ProtoPackage.newBuilder();
		msgBuild = ProtoMessage.newBuilder();
		insBuild = ProtoInstance.newBuilder();
		alienBld = Alien.newBuilder();
		
		
		pkgBuild.setName("Joe's Protos");
		pkgBuild.setFilePath("/e/temp/joe.protoPkg");
		msgBuild.setName("Alien");
		msgBuild.setClassName(Alien.class.getName());
		insBuild.setName("PlutoScar");
		alienBld.setName("PlutoScar");
		alienBld.setId(23);
		alienBld.setSpecies("Plutonian");
		alienBld.addTongue(
				Language.newBuilder().setGrammer("Pluto").setType(PlanetType.AQUA).build());
		insBuild.setMessage(alienBld.build().toByteString());
		msgBuild.addMessage(insBuild.build());
		pkgBuild.addMsgs(msgBuild);
		packages.add(pkgBuild.build());
		
		return packages;
	}
	
	public static void main(String args[])
	{
		FileDialog fil = new FileDialog(Display.getCurrent().getActiveShell());
		
		
		File file = new File("/C/Users/sloppyjoe");
		if(file.exists())
			System.out.println("YAY");
		else{
			file.mkdir();
		}
	}
}
