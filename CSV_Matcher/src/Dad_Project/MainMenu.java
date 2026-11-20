package Dad_Project;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventObject;
import java.util.HashMap;


public class MainMenu {
	private JFrame frame;
	private final File folder = new File(System.getProperty("user.dir"),"/InputFiles");
	public String selectedDatabase = "empty";
	public JLabel optionsLabel = new JLabel("options");
	public JPanel optionsPanel;
	public HashMap<String,EntryList> tables;//
	public HashMap<String,EntryList> tablesToSort;
	public JButton[] databaseJButtons;
	public JCheckBox toggleCompress;
	
	public MainMenu() {
		this.tables = new HashMap<String,EntryList>();
		this.tablesToSort = new HashMap<String,EntryList>();
		this.frame = new JFrame();
		this.optionsPanel = optionsPanel();
		mainMenuFrame();
		frame.setVisible(true);
		
	}
	public void dataBaseChanged(ActionEvent e) {
		
	}
	/**
	 * returns all the files in the input csv;
	 * @return
	 */
	private JButton[] listFileButtons() {
		File[] files = folder.listFiles();
		
		JButton[] jbuttonArray = new JButton[files.length];
		if(files!= null) {
			for(int i =0; i<files.length;i++) {
				tables.put(files[i].getName(), new EntryList(files[i].getName()));
				jbuttonArray[i]	= new CustomButton(files[i].getName(),ButtonOptions.SELECT_DATABASE,this);
				
			}
		}
		files = null;
		databaseJButtons = jbuttonArray;
		return jbuttonArray;

	}
	
	private JPanel optionsPanel() {
		JPanel optionsPerDatabase = new JPanel();
		optionsPerDatabase.setLayout(new BoxLayout(optionsPerDatabase,BoxLayout.Y_AXIS));
		toggleCompress = new JCheckBox("Compress");
		toggleCompress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tables.get(selectedDatabase).toggleCompressed();
			}
		});
		
		JButton savebutton = new CustomButton("save",ButtonOptions.SAVE_LISTENER,this);
		JButton openbutton = new CustomButton("open",ButtonOptions.OPEN_DATABASE,this);
		JButton toggleSortButton = new CustomButton("add to sort",ButtonOptions.TOGGLE_SORT,this);
		JButton sortTablesButton = new CustomButton("sort selected tables",ButtonOptions.SORT_TABLES,this);
		optionsPerDatabase.add(optionsLabel);
		optionsPerDatabase.add(toggleCompress);
		optionsPerDatabase.add(savebutton);
		optionsPerDatabase.add(openbutton);
		optionsPerDatabase.add(toggleSortButton);
		optionsPerDatabase.add(sortTablesButton);
		return optionsPerDatabase;
	}

	
	
	
	private void mainMenuFrame() {
		int framewidth = 30*16;
		int frameheight = 30*9;
		frame.setTitle("Main Menu");
		frame.setSize(framewidth,frameheight);
		//frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		for(JButton button: listFileButtons()) {
			panel.add(button);
		}
		
		JScrollPane jp = new JScrollPane(panel);
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper,BoxLayout.Y_AXIS));
		JLabel inputLabel = new JLabel("input databases");
		wrapper.add(inputLabel);
		wrapper.add(jp);
		wrapper.setPreferredSize(new Dimension(framewidth/2,frameheight));
		
		JPanel optionsPerDatabase = optionsPanel();
				
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
		mainPanel.add(wrapper);
		mainPanel.add(optionsPerDatabase);
		frame.add(mainPanel);
		
	
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	

}
