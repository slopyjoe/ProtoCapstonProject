package protodebugger.util.io;


public interface IConnector {

	/**
	 * <b>connect</b> - Connects to the I/O specific devices.
	 *  
	 * @return {@link Boolean} - T/F whether {@link IConnector} 
	 * connected successfully.
	 */
	public boolean connect();
	
	/**
	 * <b>disconnect</b> - Disconnects from the I/O specific devices.
	 *  
	 * @return {@link Boolean} - T/F whether {@link IConnector} 
	 * disconnected successfully.
	 */
	public boolean disconnect();
	
	/**
	 * <b>sendMsg</b> - Sends a message to the I/O specific devices.
	 *  
	 * @return {@link Boolean} - T/F whether {@link IConnector} 
	 * sent message successfully.
	 */
	public boolean sendMsg(Object msg);
	
}
