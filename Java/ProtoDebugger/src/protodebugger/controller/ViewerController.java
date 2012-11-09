package protodebugger.controller;

import protodebugger.model.ProtoPackageModel;
import protodebugger.views.ProtoCacheViewer;

/**
 * 
 * ViewerController
 * DESCRIPTION: {@link ProtoCacheViewer} controller.
 * Keeps {@link ProtoCacheViewer} in sync with {@link ProtoPackageModel}
 * 
 * PACKAGE: protodebugger.controller
 * @author sloppyjoe -Initial release
 *
 */
public enum ViewerController {

	INSTANCE;
	private ProtoPackageModel packageModel  = new ProtoPackageModel();
	
	
	private ViewerController()
	{
	}
	
	/**
	 * Returns the current model in control
	 * 
	 * @return {@link ProtoPackageModel}
	 */
	public final ProtoPackageModel getModel()
	{
		return packageModel;
	}
	
}
