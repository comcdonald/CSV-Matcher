package Dad_Project;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.*;
import javax.swing.table.*;


public class GuiWindow {
	
	private static final String ZOOM_IN = "Zoom In";
	private static final String ZOOM_OUT = "Zoom Out";
	private static final String PRINT = "Print";
	private static final int ZOOM_IN_INC = 2;
	private static final int ZOOM_OUT_INC = -2;
	
	private JFrame frame;
	
	private EntryList EList;

	
	public GuiWindow(EntryList entryList,String title) {
		this.EList = entryList;
		String[][] table = EList.getTableofData();
		String[] columnHeaders = EList.getArrayOfDataTypes();
		JTable jTable = buildTable(table,columnHeaders);
		buildFrame(jTable,title);
	}
	public GuiWindow(String[][] table,String title) {
		String[] columnHeaders = new String[table[0].length];
		for(int i =0;i<columnHeaders.length;i++) {
			columnHeaders[i]= "-";
		}
		JTable jTable = buildTable(table,columnHeaders);
		buildFrame(jTable,title);
	}
	
	private void buildFrame(JComponent jComponent, String title) {
		int framewidth = 1000;
		int frameheight = 400;
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(framewidth,frameheight);
		frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(false);
		frame.setLocationByPlatform(true);
		
		JScrollPane sp = new JScrollPane(jComponent); // gives table a vertical scrollbar
		JCheckBox areHeadersAvailable = new JCheckBox("Headers?");
		JPanel topPanel = new JPanel();
		topPanel.add(areHeadersAvailable);
		frame.add(topPanel);
		//frame.add(areHeadersAvailable);
		frame.add(sp);
		
		
	}
	
	private JTable buildTable(String[][] table,String[] columnHeaders) {
		JTable jTable = new JTable(table,columnHeaders);
		resizeColumnWidth(jTable);
		jTable.getColumnModel().getColumn(0).setHeaderValue("test");
		JTableHeader header = jTable.getTableHeader();
		header.setReorderingAllowed(false);
		//header.setDefaultRenderer(new EditableHeaderRenderer());
		header.addMouseListener(new HeaderMouseListener(jTable,EList));
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return jTable;
	}
	
	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 70; // Min width
			for (int row = 0; row < table.getRowCount();row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width +5 , width);
				
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}
	static class EditableHeaderRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7732092793615173025L;
		/**
		 * 
		 */

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			JTextField editor = new JTextField(value.toString());
			//editor.setBorder(BorderFactory.createEmptyBorder());
			return editor;
		}
		
	}
	/**
	 * Allows user to edit header value, header value is passed on to EntryList upon edit completion
	 */
	static class HeaderMouseListener extends MouseAdapter {
		private final JTable table;
		private EntryList EList;
		
		public HeaderMouseListener(JTable table,EntryList EList) {
			this.table = table;
			this.EList = EList;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			JTableHeader header = table.getTableHeader();
			int column = header.columnAtPoint(e.getPoint());
			String currentName = table.getColumnName(column);
			
			String newName = JOptionPane.showInputDialog(header,"Edit Column Name:", currentName);
			if (newName != null && !newName.trim().isEmpty()) {
				TableColumn tableColumn = table.getColumnModel().getColumn(column);
				tableColumn.setHeaderValue(newName);
				EList.setDataType(column, newName);

				header.repaint();
			}
		}
	}
	
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	
}
