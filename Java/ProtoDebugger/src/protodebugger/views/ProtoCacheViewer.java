package protodebugger.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import protodebugger.cache.CachedProtos;
import protodebugger.controller.ViewerController;
import protodebugger.model.ProtoPackageModel;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.util.ProtoEvents;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;

public class ProtoCacheViewer extends ViewPart implements PropertyChangeListener{

	public static final String ID = "protodebugger.views.ProtoCacheViewer"; //$NON-NLS-1$
	private TreeItem root;
	
	public ProtoCacheViewer() {
		
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
	    TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	    viewer.setContentProvider(new ProtoCacheContentProvider());
	    viewer.setLabelProvider(new ProtoCacheLabelProvider());
	    // Expand the tree
	    viewer.setAutoExpandLevel(2);
	    // Provide the input to the ContentProvider
	    viewer.setInput(ViewerController.INSTANCE.getModel());
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(ProtoEvents.valueOf(evt.getPropertyName()) == ProtoEvents.CACHED_LOADED)
		{
			root.clearAll(true);
			Map<String, List<GeneratedMessage>> cachedProtos = CachedProtos.INSTANCE.getCacheProtos();
			for(String proto : cachedProtos.keySet())
			{
				TreeItem protoItem = new TreeItem(root, SWT.None);
				protoItem.setText(proto);
				for(GeneratedMessage msg : cachedProtos.get(proto))
				{
					TreeItem protoMsgItem = new TreeItem(protoItem, SWT.None);
					protoMsgItem.setText("Message");
					protoMsgItem.setData(msg);
				}
			}
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
	private class ProtoCacheContentProvider implements ITreeContentProvider{

		private ProtoPackageModel model  = new ProtoPackageModel();
		
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
			model.addProtoPkg(pkgBuild.build());
			pkgBuild.setName("Hello2");
			pkgBuild.setFilePath("filePath");
			msgBuild.setName("Message2");
			msgBuild.addClassName("class name 1");
			msgBuild.addClassName("class name 2");
			msgBuild.addMessage(ByteString.copyFrom("adf".getBytes()));
			pkgBuild.addMsgs(msgBuild);
			model.addProtoPkg(pkgBuild.build());
		}
		
		@Override
		public void dispose() {
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		/*	if(newInput instanceof ProtoPackage)
			{
				this.model = (ProtoPackageModel) newInput;
			}*/
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if(model == null)
				return Collections.EMPTY_LIST.toArray();
			return model.getPackages().toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof ProtoPackage)
			{
				return ((ProtoPackage)parentElement).getMsgsList().toArray();
			}else if(parentElement instanceof ProtoMessage)
			{
				return((ProtoMessage)parentElement).getClassNameList().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return (element instanceof ProtoPackage ||
					element instanceof ProtoMessage);
		}
		
	}
	
	private class ProtoCacheLabelProvider extends LabelProvider{
		@Override
		public String getText(Object element)
		{
			if(element instanceof ProtoPackage)
			{
				ProtoPackage proto = (ProtoPackage) element;
				return proto.getName();
			}
			else if(element instanceof ProtoMessage)
			{
				ProtoMessage proto = (ProtoMessage) element;
				return proto.getName();
			}else {
				return element.toString();
			}
		}
		  @Override
		  public Image getImage(Object element) {
		    if (element instanceof ProtoPackage ||
					element instanceof ProtoMessage) {
		      return PlatformUI.getWorkbench().getSharedImages()
		          .getImage(ISharedImages.IMG_OBJ_FOLDER);
		    }
		    return PlatformUI.getWorkbench().getSharedImages()
		    .getImage(ISharedImages.IMG_OBJ_FILE);
		  }

	}
}
