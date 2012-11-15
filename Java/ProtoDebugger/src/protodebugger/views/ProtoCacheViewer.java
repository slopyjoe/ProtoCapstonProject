package protodebugger.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import protodebugger.controller.EditorController;
import protodebugger.controller.ViewerController;
import protodebugger.model.ProtoPackageModel;
import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;
import protodebugger.util.ProtoEvents;
import protodebugger.views.menus.ProtoCacheViewerMenu;

public class ProtoCacheViewer extends ViewPart implements PropertyChangeListener{

	public static final String ID = "protodebugger.views.ProtoCacheViewer"; //$NON-NLS-1$
	private TreeViewer viewer;
	private ProtoCacheViewerMenu menuListener;

	public ProtoCacheViewer() 
	{
		ViewerController.INSTANCE.addChangeListener(this);
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

	    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	    viewer.setContentProvider(new ProtoCacheContentProvider());
	    viewer.setLabelProvider(new ProtoCacheLabelProvider());
	    
	    // Provide the input to the ContentProvider
	    viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeViewer view = (TreeViewer) event.getViewer();
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
			    Object selectedNode = thisSelection.getFirstElement(); 
			    if(selectedNode instanceof ProtoInstance &&
			    		view.getInput() instanceof ProtoPackageModel)
			    {
			    	ProtoPackageModel derp =  (ProtoPackageModel)view.getInput();
			    	ProtoMessage msg = derp.getMessageForInstance((ProtoInstance)selectedNode);
			    	if(msg != null)
			    	{
			    		EditorController.INSTANCE.editMessage(msg, (ProtoInstance) selectedNode);
			    	}
			    }
			}
		});
	    Tree tree = viewer.getTree();
		Menu menu = new Menu(tree);
		menuListener = new ProtoCacheViewerMenu(menu);
		tree.setMenu(menu);
		menu.addMenuListener(menuListener);
	    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {				
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
			    Object selectedNode = thisSelection.getFirstElement(); 
			    if(selectedNode instanceof ProtoPackage)
			    {
			    	menuListener.setModel((ProtoPackage)selectedNode);
			    }else if(selectedNode instanceof ProtoMessage){
			    	menuListener.setModel((ProtoMessage)selectedNode);
			    }else if(selectedNode instanceof ProtoInstance){
			    	menuListener.setModel((ProtoInstance)selectedNode);
			    }
			}
		});		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(ProtoEvents.valueOf(evt.getPropertyName()) == ProtoEvents.CACHED_LOADED)
		{
			if(evt.getNewValue() instanceof ProtoPackageModel)
			{
				ProtoPackageModel model = (ProtoPackageModel)evt.getNewValue();
				viewer.setInput(model);
				viewer.refresh();
			}
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
	private class ProtoCacheContentProvider implements ITreeContentProvider{

		private ProtoPackageModel model  = new ProtoPackageModel();
		

		
		@Override
		public void dispose() {
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if(newInput instanceof ProtoPackageModel)
			{
				this.model = (ProtoPackageModel) newInput;
			}
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
				return((ProtoMessage)parentElement).getMessageList().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return model.getParent(element);
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
			}else if(element instanceof ProtoInstance){
				ProtoInstance proto = (ProtoInstance) element;
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
