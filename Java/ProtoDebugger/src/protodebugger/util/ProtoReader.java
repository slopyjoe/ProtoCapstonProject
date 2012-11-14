package protodebugger.util;

import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class ProtoReader 
{
	public static ProtoPackage readProto(File file)
	{
		try
		{
			FileInputStream reader = new FileInputStream(file);
			ProtoPackage protoPkg = ProtoPackage.parseFrom(reader);
			Logger.INSTANCE.writeInfo(protoPkg.toString());
			return protoPkg;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Logger.INSTANCE.writeError(e.getMessage());
		}
		
		return null;
	}
}
