package protodebugger.views.menus;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import protodebugger.controller.EditorController;
import protodebugger.model.descriptors.IFieldDescriptor;
import protodebugger.model.descriptors.IMessageDescriptor;
import protodebugger.util.ParseGeneratedMessage;
import protodebugger.util.ProtoEvents;

public class ProtoEditorMenu extends SelectionAdapter implements MenuListener {

	private Menu menu;
	private IMessageDescriptor<?> desc;
	private MenuItem addMenuItem;
	private MenuItem removeMenuItem;

	public ProtoEditorMenu(Menu menu) {
		this.menu = menu;
		addMenuItem = new MenuItem(menu, SWT.CASCADE);
		addMenuItem.setText("Add Proto");
		removeMenuItem = new MenuItem(menu, SWT.CASCADE);
		removeMenuItem.setText("Remove Proto");
	}

	public void setModel(IMessageDescriptor<?> desc) {
		this.desc = desc;
		System.out.println("Changing the desc");
	}

	@Override
	public void menuHidden(MenuEvent e) {
	}

	@Override
	public void menuShown(MenuEvent e) {
		System.out.println("Calling Menu shown");
		resetMenu();
		if (desc != null) {
			createAddSubItems();
			createRemoveSubItems();
		}
	}

	private void resetMenu() {
		if(addMenuItem.getMenu() != null)
		addMenuItem.getMenu().dispose();
		if(removeMenuItem.getMenu() != null)
		removeMenuItem.getMenu().dispose();
	}

	private void createAddSubItems() {
		if (!desc.getRepeatedDescriptors().isEmpty()) {
			Menu addMenu = new Menu(addMenuItem);
			addMenuItem.setMenu(addMenu);
			for (IFieldDescriptor<?> descriptor : desc.getRepeatedDescriptors()) {
				System.out.println("Adding " + descriptor.getName());
				MenuItem item = new MenuItem(addMenu, SWT.NONE);
				item.setText(descriptor.getName());
				item.setData(descriptor);
				item.addSelectionListener(this);
			}
			addMenuItem.setEnabled(true);
		}else{
			addMenuItem.setEnabled(false);
		}
	}

	private void createRemoveSubItems() {
		if (!desc.getAddedDescriptors().isEmpty()) {
			Menu removeMenu = new Menu(removeMenuItem);
			removeMenuItem.setMenu(removeMenu);
			for (IFieldDescriptor<?> descriptor : desc.getAddedDescriptors()) {
				MenuItem item = new MenuItem(removeMenu, SWT.NONE);
				item.setText(descriptor.getName());
				item.setData(descriptor);
				item.addSelectionListener(this);
			}
			removeMenuItem.setEnabled(true);
		}else{
			removeMenuItem.setEnabled(false);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		System.out.println(e.getClass().getName());
		if (e.getSource() instanceof MenuItem) {
			MenuItem item = (MenuItem) e.getSource();
			if (item.getParent().getParentMenu() != null) {
				if (item.getParent().equals(addMenuItem.getMenu())) {
					Object data = item.getData();
					if (data != null && data instanceof IFieldDescriptor<?>) {
						desc.addDescriptor(ParseGeneratedMessage
								.parseFieldDescriptor(((IFieldDescriptor<?>) data)
										.getProtoField()));
					}
				} else if (item.getParent().equals(removeMenuItem.getMenu())) {
					Object data = item.getData();
					if (data != null && data instanceof IFieldDescriptor<?>) {
						desc.removeDescriptor((IFieldDescriptor<?>) data);
					}
				}
				EditorController.INSTANCE.fireEvent(ProtoEvents.UPDATE_EDITOR,
						null, null);
			}
		}
	}

}
