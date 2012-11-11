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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
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
	private Text javaFieldTxt;
	private Text nameFieldTxt;
	private Composite editedFieldWdg;
	private TreeViewer treeViewer;
	
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
		parent.setLayout(new FormLayout());
		
		treeViewer = new TreeViewer(parent, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		FormData fd_tree = new FormData();
		fd_tree.top = new FormAttachment(0, 10);
		fd_tree.left = new FormAttachment(0, 10);
		fd_tree.right = new FormAttachment(0, 160);
		fd_tree.bottom = new FormAttachment(0, 354);
		tree.setLayoutData(fd_tree);
		treeViewer.setContentProvider(new ProtoEditorTreeContentProvider());
		treeViewer.setLabelProvider(new ProtoEditorTreeLabelProvider());
		treeViewer.setInput(model);
		Label nameLbl = new Label(parent,  SWT.NONE);
		nameLbl.setText("Name: ");
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 57);
		fd_lblNewLabel.left = new FormAttachment(tree, 45);
		nameLbl.setLayoutData(fd_lblNewLabel);
		
		Label typeLbl = new Label(parent, SWT.NONE);
		typeLbl.setText("Type: ");
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.left = new FormAttachment(tree, 45);
		fd_lblNewLabel_1.top = new FormAttachment(nameLbl, 54);
		typeLbl.setLayoutData(fd_lblNewLabel_1);
		
		Label valueLbl = new Label(parent, SWT.NONE);
		valueLbl.setText("Value: ");
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.right = new FormAttachment(100, -204);
		fd_lblNewLabel_2.left = new FormAttachment(tree, 45);
		fd_lblNewLabel_2.top = new FormAttachment(typeLbl, 54);
		valueLbl.setLayoutData(fd_lblNewLabel_2);
		
		javaFieldTxt = new Text(parent, SWT.NONE);
		javaFieldTxt.setEditable(false);
		fd_lblNewLabel_1.right = new FormAttachment(javaFieldTxt, -56);
		FormData fd_txtNewText_1 = new FormData();
		fd_txtNewText_1.right = new FormAttachment(100, -87);
		fd_txtNewText_1.left = new FormAttachment(0, 316);
		fd_txtNewText_1.top = new FormAttachment(typeLbl, 0, SWT.TOP);
		javaFieldTxt.setLayoutData(fd_txtNewText_1);
		
		nameFieldTxt = new Text(parent, SWT.NONE);
		nameFieldTxt.setEditable(false);
		FormData fd_txtNewtext = new FormData();
		fd_txtNewtext.left = new FormAttachment(javaFieldTxt, -58);
		fd_txtNewtext.right = new FormAttachment(javaFieldTxt, 0, SWT.RIGHT);
		fd_txtNewtext.top = new FormAttachment(nameLbl, -3, SWT.TOP);
		nameFieldTxt.setLayoutData(fd_txtNewtext);
		
		editedFieldWdg = new Composite(parent, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(javaFieldTxt, 64, SWT.BOTTOM);
		fd_composite.top = new FormAttachment(javaFieldTxt, 34);
		fd_composite.left = new FormAttachment(valueLbl, 56);
		fd_composite.right = new FormAttachment(100, -65);
		editedFieldWdg.setLayoutData(fd_composite);
		
/*		parent.setLayout(new FormLayout());
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Tree tree = viewer.getTree();
		viewer.setContentProvider(new ProtoEditorTreeContentProvider());
	    viewer.setLabelProvider(new ProtoEditorTreeLabelProvider());
	    
	    // Provide the input to the ContentProvider
	    viewer.setInput(model);
	    
		
		Label nameLbl = new Label(parent, SWT.NONE);
		nameLbl.setText("Name: ");
		Label javaLbl = new Label(parent, SWT.NONE);
		javaLbl.setText("Type: ");
		Label valueLbl = new Label(parent, SWT.NONE);
		valueLbl.setText("Value: ");
		
		nameFieldTxt = new Text(parent, SWT.NONE);
		
		javaFieldTxt = new Text(parent, SWT.NONE);
		
		editedFieldWdg = new Composite(parent, SWT.NONE);
	 //   editedFieldWdg.setLayout(new FillLayout(SWT.HORIZONTAL));
	    
	    FormData data = new FormData();
	    data.top = new FormAttachment(tree, 0, SWT.TOP);
	    data.left = new FormAttachment(tree, 0, SWT.CENTER);
	    data.bottom = new FormAttachment(tree, 0, SWT.BOTTOM);
	    tree.setLayoutData(data);
	    
	    data = new FormData();
	    data.top = new FormAttachment(nameLbl, 5, SWT.TOP);
	    data.left = new FormAttachment(tree, 5, SWT.LEFT);
	    data.bottom = new FormAttachment(javaLbl, 5, SWT.BOTTOM);
	    nameLbl.setLayoutData(data);
	    
	    data = new FormData();
	    data.top = new FormAttachment(nameLbl, 5, SWT.TOP);
	    data.left = new FormAttachment(tree, 5, SWT.LEFT);
	    data.bottom = new FormAttachment(valueLbl, 5, SWT.BOTTOM);
	    javaLbl.setLayoutData(data);
	    
	    data = new FormData();
	    data.top = new FormAttachment(javaLbl, 5, SWT.TOP);
	    data.left = new FormAttachment(tree, 5, SWT.LEFT);
	    data.bottom = new FormAttachment(valueLbl, 5, SWT.BOTTOM);
	    valueLbl.setLayoutData(data);
	    
	    data = new FormData();
	    data.top = new FormAttachment(nameFieldTxt, 5, SWT.TOP);
	    data.left = new FormAttachment(nameLbl, 5, SWT.LEFT);
	    data.bottom = new FormAttachment(javaFieldTxt, 5, SWT.BOTTOM);
	    nameFieldTxt.setLayoutData(data);
	    
	    data = new FormData();
	    data.top = new FormAttachment(nameFieldTxt, 5, SWT.TOP);
	    data.left = new FormAttachment(javaLbl, 5, SWT.LEFT);
	    data.bottom = new FormAttachment(valueLbl, 5, SWT.BOTTOM);
	    javaFieldTxt.setLayoutData(data);
	    
	    data = new FormData();
	    data.top = new FormAttachment(javaFieldTxt, 5, SWT.TOP);
	    data.left = new FormAttachment(valueLbl, 5, SWT.LEFT);
	    data.bottom = new FormAttachment(editedFieldWdg, 5, SWT.BOTTOM);
	    editedFieldWdg.setLayoutData(data);
	    
	    parent.pack();*/
	    
	    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {				
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
			    Object selectedNode = thisSelection.getFirstElement(); 
			    if(selectedNode instanceof AbstractFieldDescriptor<?>)
			    {
		
			    	AbstractFieldDescriptor<?> msg = (AbstractFieldDescriptor<?>)selectedNode;
			    	System.out.println("editing " + msg.getName());
			    	javaFieldTxt.setText(msg.getName());
			    	nameFieldTxt.setText(msg.getProtoField().getJavaType().name());
			    	ParseGeneratedMessage.parseSWT(msg, editedFieldWdg);
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
