package protodebugger.test.packages;

import java.util.ArrayList;
import java.util.List;

import org.test.AlienSpeciesProto.Alien;
import org.test.AlienSpeciesProto.Alien.Language;
import org.test.AlienSpeciesProto.Alien.PlanetType;

import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

public class ProtoPackages {


	public static List<ProtoPackage> getPackages()
	
	{
		List<ProtoPackage> packages = new
				ArrayList<ProtoPackage>();
		ProtoPackage.Builder pkgBuild = ProtoPackage.newBuilder();
		ProtoMessage.Builder msgBuild = ProtoMessage.newBuilder();
		ProtoInstance.Builder insBuild = ProtoInstance.newBuilder();
		Alien.Builder alienBld = Alien.newBuilder();
		
		
		pkgBuild.setName("Alian's Protos");
		pkgBuild.setFilePath("/e/temp/alian.protoPkg");
		msgBuild.setName("Alien");
		msgBuild.setClassName(Alien.class.getName());
		insBuild.setName("MarsBar");
		alienBld.setName("MarsBar");
		alienBld.setId(23);
		alienBld.setSpecies("Martian");
		alienBld.addTongue(
				Language.newBuilder().setGrammer("Frasier").setType(PlanetType.AQUA).build());
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
		ArrayList<Integer> h = new ArrayList<Integer>();
		System.out.println(h.getClass().getGenericSuperclass());
	}
}
