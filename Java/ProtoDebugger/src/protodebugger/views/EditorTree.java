package protodebugger.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.test.AlienSpeciesProto.Alien;

import protodebugger.controller.EditorController;
import protodebugger.model.ProtoMessageGeneric;
import protodebugger.model.descriptors.generic.AbstractFieldDescriptor;
import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.IMessageDescriptor;
import protodebugger.model.swt.SWTFieldWrapper;
import protodebugger.util.ParseGeneratedMessage;
import protodebugger.util.ProtoEvents;
import protodebugger.views.menus.ProtoEditorMenu;

public class EditorTree extends ViewPart implements PropertyChangeListener {
	private final ProtoMessageGeneric model  = new ProtoMessageGeneric();
	private Text javaFieldTxt;
	private Text nameFieldTxt;
	private Composite editedFieldCmp;
	private Widget editedFieldWdg;
	private TreeViewer treeViewer;
	private SWTFieldWrapper currentWrap;
	private ProtoEditorMenu menuListener;
	
	{
		model.setGenMsg(Alien.getDefaultInstance());
	}
	
	public EditorTree() {
		EditorController.INSTANCE.addPropertyListener(this);
	}

	@Override
	public void createPartControl(final Composite parent) {
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
		EditorController.INSTANCE.setModel(model);
		
		editedFieldCmp = new Composite(parent, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 153);
		fd_composite.right = new FormAttachment(100, -10);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(tree, 39);
		
		editedFieldCmp.setLayout(new GridLayout(2, true));
		GridData gData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		//gData.horizontalAlignment = SWT.LEFT;
		//gData.verticalAlignment = SWT.TOP;
		
		
		Label nameLbl = new Label(editedFieldCmp,  SWT.NONE);
		nameLbl.setText("Name: ");
		nameLbl.setLayoutData(gData);
		
		nameFieldTxt = new Text(editedFieldCmp, SWT.NONE);
		nameFieldTxt.setLayoutData(gData);
		//new Label(comp,  SWT.NONE);
		
		Label typeLbl = new Label(editedFieldCmp, SWT.NONE);
		typeLbl.setText("Type: ");
		typeLbl.setLayoutData(gData);
		
		javaFieldTxt = new Text(editedFieldCmp, SWT.NONE);
		javaFieldTxt.setLayoutData(gData);
		//new Label(comp,  SWT.NONE);
		
		Label valueLbl = new Label(editedFieldCmp, SWT.NONE);
		valueLbl.setText("Value: ");
		valueLbl.setLayoutData(gData);
				
		
		
		editedFieldCmp.setLayoutData(fd_composite);
		
		Menu menu = new Menu(tree);
		menuListener = new ProtoEditorMenu(menu);
		tree.setMenu(menu);
		menu.addMenuListener(menuListener);
	    treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {				
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
			    Object selectedNode = thisSelection.getFirstElement(); 
			    if(selectedNode instanceof AbstractFieldDescriptor<?>)
			    {
			    	
			    	if(currentWrap != null){
			    		currentWrap.widgetValueToProtoField();
			    		currentWrap.getSwtWidget().dispose();
			    		editedFieldCmp.layout();
			    	}
			    	AbstractFieldDescriptor<?> msg = (AbstractFieldDescriptor<?>)selectedNode;
			    	System.out.println("editing " + msg.getName());
			    	javaFieldTxt.setText(msg.getProtoField().getJavaType().name());
			    	nameFieldTxt.setText(msg.getName());
			    	currentWrap = ParseGeneratedMessage.parseSWT(msg, editedFieldCmp);
			    	currentWrap.setFieldDescriptor(msg);
			    	currentWrap.protoValueToWidget();
			    	menuListener.setModel(null);
			    	editedFieldCmp.layout();
			    	
			    }
			    if(selectedNode instanceof IMessageDescriptor<?>)
			    {
			    	menuListener.setModel((IMessageDescriptor<?>)selectedNode);
			    }
			}
		});
	}
	private class ProtoEditorTreeContentProvider implements ITreeContentProvider{

		@Override
		public void dispose() {
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

		@Override
		public Object[] getElements(Object inputElement) {
			return new Object[]{model.getProto()};//model.getDescriptors().toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof IMessageDescriptor<?>)
			{
				return ((IMessageDescriptor<?>)parentElement).getDescriptors().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;//model.getParent(element);
		}

		@Override
		public boolean hasChildren(Object element) {
			return (element instanceof IMessageDescriptor<?>);
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
		    if (element instanceof IMessageDescriptor<?>) {
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try{
			ProtoEvents event = ProtoEvents.valueOf(evt.getPropertyName());
			if(event == ProtoEvents.UPDATE_EDITOR)
				treeViewer.refresh();
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
