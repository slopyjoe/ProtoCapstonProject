package protodebugger.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import com.google.protobuf.GeneratedMessage;

import protodebugger.cache.CachedProtos;
import protodebugger.util.ProtoEvents;

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
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(114, 217, 64, 64);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.right = new FormAttachment(100);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new TreeColumnLayout());
		
		TreeViewer treeViewer = new TreeViewer(composite, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		
		root = new TreeItem(tree, SWT.NONE);
		root.setText("Proto");
		

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
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
}
