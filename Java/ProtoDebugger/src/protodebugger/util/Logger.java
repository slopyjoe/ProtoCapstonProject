package protodebugger.util;

public enum Logger {

	INSTANCE;
	
	public void writeInfo(String msg){
		System.out.println("INFO - "+msg);
	}
	public void writeError(String msg){
		System.err.println("ERROR - "+msg);
	}
	public void writeDebug(String msg){
		System.out.println("DEBUG - "+msg);
	}
	
}
