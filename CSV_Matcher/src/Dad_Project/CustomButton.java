package Dad_Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;

public class CustomButton extends JButton {
	public static final Dimension DEFAULT_SIZE = new Dimension(100,25);
	private ButtonOptions option;
	private MainMenu menu;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Compatible options: SAVE_LISTENER
	 * @param label
	 * @param option
	 * @param EList
	 */
	
	
	public CustomButton(String label,ButtonOptions option,MainMenu menu) {
		super();
		this.option = option;
		this.menu = menu;
		this.setText(label);
		setListener();
	}
	
	private void setListener() {
		switch(option) {
		case SAVE_LISTENER:
			this.addMouseListener(new saveListener(menu));
			break;
		case SELECT_DATABASE:
			this.addMouseListener(new selectDataBaseListener(this.getText(),menu));
			break;
		case OPEN_DATABASE:
			this.addMouseListener(new openDataBaseListener(menu));
			break;
		case TOGGLE_SORT:
			this.addMouseListener(new toggleSortListener(menu));
			break;
		case SORT_TABLES:
			this.addMouseListener(new sortTablesListener(menu));
			break;
		}
	}
	
	
	private static class saveListener extends MouseAdapter {
		private MainMenu menu;
		
		public saveListener(MainMenu menu) {
			this.menu = menu;
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.print("save clicked");
			if (menu.selectedDatabase.equals("empty")==false) {
				saveFile();
			}else {
				System.out.print("no file selected");
			}
			
		}
		
		private void saveFile() {
			FileManager fm = new FileManager();
			fm.writeFile(fm.getBaseDirectory()+"/OutputFiles/"+"Out"+menu.selectedDatabase, menu.tables.get(menu.selectedDatabase).toString());
			System.out.print("saved");
		}
	}
	
	private static class selectDataBaseListener extends MouseAdapter {
		private String name;
		private MainMenu menu;
		public selectDataBaseListener(String buttonName,MainMenu menu) {
			this.name = buttonName;
			this.menu = menu;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			menu.selectedDatabase = name;
			menu.optionsLabel.setText(name);
			menu.optionsLabel.repaint();
			menu.toggleCompress.setSelected(menu.tables.get(name).getCompressedState());
			System.out.println(name+" selected");
		}
	}
	
	private static class openDataBaseListener extends MouseAdapter {
		private MainMenu menu;
		public openDataBaseListener(MainMenu menu) {
			this.menu = menu;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("opening "+menu.selectedDatabase);	
			GuiWindow window = new GuiWindow(menu.tables.get(menu.selectedDatabase),menu.selectedDatabase);
			window.setVisible();
		}
	}
	private static class toggleSortListener extends MouseAdapter {
		private MainMenu menu;
		public toggleSortListener(MainMenu menu) {
			this.menu = menu;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if (menu.selectedDatabase.equals("empty")==false) {
				menu.tables.get(menu.selectedDatabase).toggleSortState();
				boolean sortState = menu.tables.get(menu.selectedDatabase).getSortState();
				if(sortState) {
					menu.tablesToSort.put(menu.selectedDatabase,menu.tables.get(menu.selectedDatabase));
				}else {
					menu.tablesToSort.remove(menu.selectedDatabase);
				}
				
				System.out.println(menu.tables.get(menu.selectedDatabase).getSortState());
				for(JButton button:menu.databaseJButtons) {
					if(button.getText().equals(menu.selectedDatabase)) {
						if(menu.tables.get(menu.selectedDatabase).getSortState()) {
							button.setBackground(Color.GREEN);
						}else {
							button.setBackground(null);
						}
						button.repaint();
						menu.getFrame().repaint();
					}
				}
			}else {
				System.out.println("no file selected");
			}
		}
	}
	
	private static class sortTablesListener extends MouseAdapter {
		private MainMenu menu;
		public sortTablesListener(MainMenu menu) {
			this.menu = menu;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			EntryList[] tables = menu.tablesToSort.values().toArray(new EntryList[menu.tablesToSort.size()]);
			ArrayList<EntryList> listToSend = new ArrayList<EntryList>();
			for(int i =0;i<tables.length;i++) {
				listToSend.add(tables[i]);
			}
			Sorter sorter = new Sorter(listToSend);
			String[][] sortedTable = sorter.compare();
			GuiWindow window = new GuiWindow(sortedTable,"sortedTable");
			window.setVisible();
		}
	}
}
