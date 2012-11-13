package protodebugger.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

public class ProtoWriter 
{
	public static void writeProto(Set<ProtoPackage> protoPackages)
	{
		File file;
		try
		{
			for (ProtoPackage protoPkg : protoPackages) {
				file = new File(protoPkg.getFilePath()).getAbsoluteFile();
				if (!file.exists()) {
						file.createNewFile();
				}
				System.out.println(file.getAbsolutePath());
				FileOutputStream writer = new FileOutputStream(file);
				protoPkg.toBuilder().build().writeTo(writer);
				writer.close();
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());				
		}
		
	}
}
