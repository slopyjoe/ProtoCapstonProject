package protodebugger.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class RCPProtoEditor extends ViewPart {

	public static final String ID = "protodebugger.views.RCPProtoEditor"; //$NON-NLS-1$
	private Text text;
	private Text text_1;
	private Text text_2;

	public RCPProtoEditor() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		{
			TreeViewer treeViewer = new TreeViewer(container, SWT.BORDER);
			Tree tree = treeViewer.getTree();
		}
		{
			Composite composite = new Composite(container, SWT.NONE);
			composite.setLayout(new StackLayout());
			{
				Composite composite_1 = new Composite(composite, SWT.NONE);
				composite_1.setLayout(new GridLayout(1, false));
				{
					Label lblNewLabel = new Label(composite_1, SWT.NONE);
					lblNewLabel.setText("New Label");
				}
				{
					text = new Text(composite_1, SWT.BORDER);
					text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				}
				{
					Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
					lblNewLabel_1.setText("New Label");
				}
				{
					text_1 = new Text(composite_1, SWT.BORDER);
					text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				}
				{
					Label lblNewLabel_2 = new Label(composite_1, SWT.NONE);
					lblNewLabel_2.setText("New Label");
				}
				{
					text_2 = new Text(composite_1, SWT.BORDER);
					text_2.setBounds(0, 0, 76, 21);
				}
			}
		}

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

}
