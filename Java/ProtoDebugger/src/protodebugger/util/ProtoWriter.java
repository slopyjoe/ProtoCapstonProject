package protodebugger.util;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

public class ProtoWriter 
{
	public static void writeProto(List<ProtoPackage> protoPackages)
	{
		File file;
		for (ProtoPackage protoPkg : protoPackages)
		{
			file = new File(protoPkg.getFilePath() + protoPkg.getName() + ".proto");
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
					FileOutputStream writer = new FileOutputStream(file);					
					protoPkg.toBuilder().build().writeTo(writer);				
					writer.close();
				}
				catch(IOException e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
