package protodebugger.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;

public class Blah extends Composite {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtNewText_1;
	private Text txtNewtext;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Blah(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		TreeViewer treeViewer = new TreeViewer(this, SWT.BORDER);
		Tree tree = treeViewer.getTree();
		FormData fd_tree = new FormData();
		fd_tree.top = new FormAttachment(0, 10);
		fd_tree.left = new FormAttachment(0, 10);
		fd_tree.right = new FormAttachment(0, 160);
		fd_tree.bottom = new FormAttachment(0, 354);
		tree.setLayoutData(fd_tree);
		
		Label lblNewLabel = formToolkit.createLabel(this, "New Label", SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 57);
		fd_lblNewLabel.left = new FormAttachment(tree, 45);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		
		Label lblNewLabel_1 = formToolkit.createLabel(this, "New Label", SWT.NONE);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.left = new FormAttachment(tree, 45);
		fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 54);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		
		Label lblNewLabel_2 = formToolkit.createLabel(this, "New Label", SWT.NONE);
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.right = new FormAttachment(100, -204);
		fd_lblNewLabel_2.left = new FormAttachment(tree, 45);
		fd_lblNewLabel_2.top = new FormAttachment(lblNewLabel_1, 54);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		
		txtNewText_1 = formToolkit.createText(this, "New Text", SWT.NONE);
		fd_lblNewLabel_1.right = new FormAttachment(txtNewText_1, -56);
		FormData fd_txtNewText_1 = new FormData();
		fd_txtNewText_1.right = new FormAttachment(100, -87);
		fd_txtNewText_1.left = new FormAttachment(0, 316);
		fd_txtNewText_1.top = new FormAttachment(lblNewLabel_1, 0, SWT.TOP);
		txtNewText_1.setLayoutData(fd_txtNewText_1);
		
		txtNewtext = formToolkit.createText(this, "New Text", SWT.NONE);
		txtNewtext.setText("NewText");
		FormData fd_txtNewtext = new FormData();
		fd_txtNewtext.left = new FormAttachment(txtNewText_1, -58);
		fd_txtNewtext.right = new FormAttachment(txtNewText_1, 0, SWT.RIGHT);
		fd_txtNewtext.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
		txtNewtext.setLayoutData(fd_txtNewtext);
		
		Composite composite = formToolkit.createCompositeSeparator(this);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(txtNewText_1, 64, SWT.BOTTOM);
		fd_composite.top = new FormAttachment(txtNewText_1, 34);
		fd_composite.left = new FormAttachment(lblNewLabel_2, 56);
		fd_composite.right = new FormAttachment(100, -65);
		composite.setLayoutData(fd_composite);
		formToolkit.paintBordersFor(composite);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
