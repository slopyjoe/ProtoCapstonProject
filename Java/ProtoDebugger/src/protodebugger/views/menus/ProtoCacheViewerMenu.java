package protodebugger.views.menus;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.test.AddressBookProtos.AddressBook;
import org.test.AlienSpeciesProto.AlienSpecies;

import protodebugger.controller.ViewerController;
import protodebugger.model.protos.ProtoPkgContainer.ProtoInstance;
import protodebugger.model.protos.ProtoPkgContainer.ProtoMessage;
import protodebugger.model.protos.ProtoPkgContainer.ProtoPackage;

import com.google.protobuf.GeneratedMessage;

public class ProtoCacheViewerMenu extends SelectionAdapter implements MenuListener 
{
	private Menu menu;
	private ProtoPackage pkg;
	private ProtoMessage msg;
	private ProtoInstance ins;
	private MenuItem selectMenuItem;
	private MenuItem addMenuItem;
	private MenuItem printMenuItem;
	private MenuItem sendMenuItem;
	
	private final Map<String, GeneratedMessage> messageList = new LinkedHashMap<String, GeneratedMessage>();
	{
		messageList.put("AddressBook", AddressBook.getDefaultInstance());
		messageList.put("AlienSpecies", AlienSpecies.getDefaultInstance());
	}
	

	public ProtoCacheViewerMenu(Menu menu) {
		this.menu = menu;
		init();
	}

	private void init(){
		printMenuItem = new MenuItem(menu, SWT.NONE);
		printMenuItem.setText("Print on Console");
		sendMenuItem = new MenuItem(menu, SWT.NONE);
		sendMenuItem.setText("Send");
		new MenuItem(menu, SWT.SEPARATOR);
		
	}
	
	public void setModel(ProtoPackage pkg) {
		this.pkg = pkg;
		msg = null;
		ins = null;
	}
	
	public void setModel(ProtoMessage msg){
		this.msg = msg;
		pkg = null;
		ins = null;
	}
	
	public void setModel(ProtoInstance ins){
		this.ins = ins;
		msg = null;
		pkg = null;
	}
	@Override
	public void menuHidden(MenuEvent e) {
	}

	@Override
	public void menuShown(MenuEvent e) 
	{
		resetMenu();
		if (pkg != null) {
			createSelectSubItems();
		}else if(msg != null){
			createAddItem();
		}
	}

	private void resetMenu() {
		if(selectMenuItem != null)
		{
			selectMenuItem.dispose();
			selectMenuItem = null;
		}
		if(addMenuItem != null)
		{
			addMenuItem.dispose();
			addMenuItem = null;
		}
	}

	private void createSelectSubItems() {
		
		if (!messageList.isEmpty()) {
			selectMenuItem = new MenuItem(menu, SWT.CASCADE);
			selectMenuItem.setText("Select Proto");
			Menu selectMenu = new Menu(selectMenuItem);
			selectMenuItem.setMenu(selectMenu);
			for ( String key : messageList.keySet()) 
			{
				MenuItem item = new MenuItem(selectMenu, SWT.NONE);
				item.setText(key);
				item.setData(messageList.get(key));
				item.addSelectionListener(this);
			}
			selectMenuItem.setEnabled(true);
		}
	}
	private void createAddItem() {
		addMenuItem = new MenuItem(menu, SWT.NONE);
		addMenuItem.setText("Add");
		addMenuItem.setData(msg);
		addMenuItem.addSelectionListener(this);
	}
	
	private String getProtoName(){
		String name = "NewProtoInstance";
		InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(),
				"Proto Name", "Please name this proto message", "", null);
        if(dialog.open() == Window.OK) 
        {
          name = dialog.getValue();
        }
		return name;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() instanceof MenuItem) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.equals(addMenuItem))
			{
				ViewerController.INSTANCE.newProtoInstance(msg, getProtoName());
			}
			else if (item.getParent().getParentMenu() != null) {
				if (item.getParent().equals(selectMenuItem.getMenu())) {
					Object data = item.getData();
					if (data != null && data instanceof GeneratedMessage) 
					{
						String name = getProtoName();
						ViewerController.INSTANCE.newProtoInstance(pkg, (GeneratedMessage)data, name);
					}
				} 
			}
		}
	}

}
