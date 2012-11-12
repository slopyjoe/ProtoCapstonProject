package protodebugger.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.test.AlienSpeciesProto.Alien;

import protodebugger.controller.EditorController;
import protodebugger.model.ProtoMessageGeneric;
import protodebugger.model.descriptors.generic.AbstractFieldDescriptor;
import protodebugger.model.descriptors.generic.IFieldDescriptor;
import protodebugger.model.descriptors.generic.IMessageDescriptor;
import protodebugger.util.ParseGeneratedMessage;
import protodebugger.util.ProtoEvents;
import protodebugger.views.menus.ProtoEditorMenu;

public class EditorTree extends ViewPart implements PropertyChangeListener {
	private final ProtoMessageGeneric model  = new ProtoMessageGeneric();
	private Text javaFieldTxt;
	private Text nameFieldTxt;
	private Composite editedFieldWdg;
	private TreeViewer treeViewer;
	private ProtoEditorMenu menuListener;
	
	
	{
		model.setGenMsg(Alien.getDefaultInstance());
	}
	
	public EditorTree() {
		EditorController.INSTANCE.addPropertyListener(this);
	}

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
			    	AbstractFieldDescriptor<?> msg = (AbstractFieldDescriptor<?>)selectedNode;
			    	System.out.println("editing " + msg.getName());
			    	javaFieldTxt.setText(msg.getName());
			    	nameFieldTxt.setText(msg.getProtoField().getJavaType().name());
			    	ParseGeneratedMessage.parseSWT(msg, editedFieldWdg);
			    	menuListener.setModel(null);
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
