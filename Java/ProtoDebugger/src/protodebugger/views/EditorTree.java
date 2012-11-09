package protodebugger.views;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.test.AlienSpeciesProto.Alien;

import protodebugger.model.ProtoMessageGeneric;
import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.MessageDescriptor;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;

public class EditorTree extends ViewPart {
	private final ProtoMessageGeneric model  = new ProtoMessageGeneric();
	
	{
		model.setGenMsg(Alien.getDefaultInstance());
	}
	
	public EditorTree() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	    viewer.setContentProvider(new ProtoEditorTreeContentProvider());
	    viewer.setLabelProvider(new ProtoEditorTreeLabelProvider());
	    
	    // Provide the input to the ContentProvider
	    viewer.setInput(model);
	    /*viewer.addDoubleClickListener(new IDoubleClickListener() {
			
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
		});*/
	}

	
	private class ProtoEditorTreeContentProvider implements ITreeContentProvider{

		/*private ProtoPackageModel model  = new ProtoPackageModel();
		
		{
			for(ProtoPackage pkg : ProtoPackages.getPackages())
			{
				model.addProtoPkg(pkg);
			}
		}*/
		
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
			return model.getDescriptors().toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof MessageDescriptor)
			{
				return ((MessageDescriptor)parentElement).getDescriptors().toArray();
			}else if(parentElement instanceof ProtoMessage)
			{
				return((ProtoMessage)parentElement).getMessageList().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;//model.getParent(element);
		}

		@Override
		public boolean hasChildren(Object element) {
			return (element instanceof MessageDescriptor);
		}
		
	}
	
	private class ProtoEditorTreeLabelProvider extends LabelProvider{
		@Override
		public String getText(Object element)
		{
			if(element instanceof IFieldDescriptor<?>)
			{
				IFieldDescriptor<?> fieldDescriptor = (IFieldDescriptor<?>) element;
				return fieldDescriptor.getName();
			}else {
				return element.toString();
			}
		}
		  @Override
		  public Image getImage(Object element) {
		    if (element instanceof MessageDescriptor) {
		      return PlatformUI.getWorkbench().getSharedImages()
		          .getImage(ISharedImages.IMG_OBJ_FOLDER);
		    }
		    return PlatformUI.getWorkbench().getSharedImages()
		    .getImage(ISharedImages.IMG_OBJ_FILE);
		  }

	}




	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
