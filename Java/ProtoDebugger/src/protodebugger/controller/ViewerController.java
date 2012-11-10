package protodebugger.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import protodebugger.model.ProtoPackageModel;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.views.ProtoCacheViewer;
import protodebugger.util.ProtoEvents;

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
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
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
	
	public void addProtoPkg(ProtoPackage pkg)
	{
		packageModel.addProtoPkg(pkg);
		pcs.firePropertyChange(ProtoEvents.CACHED_LOADED.name(), null, this.getModel() );		
	}
	
	public void addChangeListener(PropertyChangeListener pcl)
	{
		pcs.addPropertyChangeListener(pcl);
	}
	
}
