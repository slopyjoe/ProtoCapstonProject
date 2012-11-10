package protodebugger.views;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import org.test.AlienSpeciesProto.Alien;

import protodebugger.model.ProtoMessageGeneric;
import protodebugger.model.descriptors.generic.AbstractFieldDescriptor;
import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.MessageDescriptor;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.util.ParseGeneratedMessage;

public class EditorTree extends ViewPart {
	private final ProtoMessageGeneric model  = new ProtoMessageGeneric();
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private TreeViewer viewer;
	private Label editedFieldLbl;
	private Composite editedFieldCmp;
	{
		model.setGenMsg(Alien.getDefaultInstance());
	}
	
	public EditorTree() {
		// TODO Auto-generated constructor stub
	}

/*	@Override
	public void createPartControl(Composite parent) {
		
		TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	    viewer.setContentProvider(new ProtoEditorTreeContentProvider());
	    viewer.setLabelProvider(new ProtoEditorTreeLabelProvider());
	    
	    // Provide the input to the ContentProvider
	    viewer.setInput(model);
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
	}
*/
	@Override
	public void createPartControl(Composite parent) {
		Composite container = toolkit.createComposite(parent, SWT.NONE);
		toolkit.paintBordersFor(container);
		container.setLayout(new FormLayout());
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Tree tree = viewer.getTree();
		viewer.setContentProvider(new ProtoEditorTreeContentProvider());
	    viewer.setLabelProvider(new ProtoEditorTreeLabelProvider());
	    
	    // Provide the input to the ContentProvider
	    viewer.setInput(model);
		FormData fd_tree = new FormData();
		fd_tree.bottom = new FormAttachment(0, 392);
		fd_tree.right = new FormAttachment(0, 584);
		fd_tree.top = new FormAttachment(0, 10);
		fd_tree.left = new FormAttachment(0, 10);
		tree.setLayoutData(fd_tree);
		toolkit.paintBordersFor(tree);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(5, false));
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(tree, 54, SWT.BOTTOM);
		fd_composite.top = new FormAttachment(tree, 2);
		fd_composite.left = new FormAttachment(0, 10);
		fd_composite.right = new FormAttachment(0, 584);
		composite.setLayoutData(fd_composite);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		editedFieldLbl = new Label(composite, SWT.NONE);
		toolkit.adapt(editedFieldLbl, true, true);
		editedFieldLbl.setText("New Label");
		new Label(composite, SWT.NONE);
		
		editedFieldCmp = new Composite(composite, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 347;
		editedFieldCmp.setLayoutData(gd_composite_1);
		toolkit.adapt(editedFieldCmp);
		toolkit.paintBordersFor(editedFieldCmp);
		editedFieldCmp.setLayout(new GridLayout(1, false));
		
	    
	    
	    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {				
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
			    Object selectedNode = thisSelection.getFirstElement(); 
			    if(selectedNode instanceof AbstractFieldDescriptor<?>)
			    {
			    	AbstractFieldDescriptor<?> msg = (AbstractFieldDescriptor<?>)selectedNode;
			    	System.out.println("editing " + msg.getName());
			    	editedFieldLbl.setText(msg.getName());
			    	ParseGeneratedMessage.parseSWT(msg, editedFieldCmp);
			    }
			}
		});
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
