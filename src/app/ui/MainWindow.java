package app.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import app.MainApp;
import app.core.QuitProcess;
import app.utils.Utility;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTable table;

	DataList list;
	MainTableModel model;

	public MainWindow() {
		setIconImage(Utility.getIcon("icon.png").getImage());
		setTitle(MainApp.appName + " " + MainApp.appVersion);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 450;
		int h = 350;
		if (d.width < w) {
			w = d.width;
		}
		if (d.height < h) {
			h = d.height;
		}
		setSize(w, h);
		setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel content = new JPanel(new BorderLayout());

		Box tb = Box.createHorizontalBox();
		tb.add(Box.createRigidArea(new Dimension(10, 60)));
		
		JPanel tbPanel = new JPanel(null);

		JButton btn1 = new JButton();
		btn1.setText("Add");
		btn1.setName("ADD");
		btn1.setToolTipText("Add Data");
		decorateButton(btn1);
		btn1.setBounds(10, 15, 80, 32);
		tbPanel.add(btn1);

		JButton btn2 = new JButton();
		btn2.setText("Delete");
		btn2.setName("DELETE");
		btn2.setToolTipText("Delete Data");
		decorateButton(btn2);
		btn2.setBounds(100, 15, 80, 32);
		tbPanel.add(btn2);
		
		tb.add(tbPanel);
		content.add(tb, "North");
		
		list = new DataList();
		model = new MainTableModel();
		model.setList(list);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
		table.setFont(new Font("Tahoma", 0, 12));
		table.setRowHeight(25);
		TableColumnModel cm = table.getColumnModel();
		for (int i = 0; i < cm.getColumnCount(); i++) {
			TableColumn c = cm.getColumn(i);
			if (c.getHeaderValue().equals("")) {
				c.setPreferredWidth(25);
			} else if (c.getHeaderValue().equals("Status")) {
				c.setPreferredWidth(200);
			} else {
				c.setPreferredWidth(150);
			}
		}
		JScrollPane jsp = new JScrollPane(table);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		content.add(jsp, "Center");

		Box sb = Box.createHorizontalBox();
		sb.add(Box.createRigidArea(new Dimension(10, 50)));
		
		JPanel sbPanel = new JPanel(null);
		
		JButton btn4 = new JButton();
		btn4.setText("Exit");
		btn4.setName("EXIT");
		btn4.setToolTipText("Exit application");
		decorateButton(btn4);
		btn4.setBounds(10, 10, 80, 32);
		sbPanel.add(btn4);
		
		JButton btn5 = new JButton();
		btn5.setText("Execute");
		btn5.setName("EXECUTE");
		btn5.setToolTipText("Kill selenium drivers");
		decorateButton(btn5);
		btn5.setBounds(getWidth() - 120, 10, 80, 32);
		sbPanel.add(btn5);
		
		sb.add(sbPanel);
		content.add(sb, "South");

		add(content);
	}

	void decorateButton(JButton btn) {
		btn.setFocusPainted(false);
		btn.addActionListener(this);
		btn.setFont(new Font("Tahoma", 0, 12));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if ((evt.getSource() instanceof AbstractButton)) {
			AbstractButton b = (AbstractButton) evt.getSource();
			String id = b.getName();
			if (id == null)
				return;
			if (id.equals("ADD")) {
				addData();
			} else if (id.equals("DELETE")) {
				removeData();
			} else if (id.equals("EXIT")) {
				exitApp();
			} else if (id.equals("EXECUTE")) {
				execute();
			}
		}
	}

	void addData() {
		DataListItem item = new DataListItem();
		item.name = "Chrome";
		item.executable = "chromedriver.exe";
		item.status = "Added";
		if (!isDuplicateEntry(item)) {
			list.add(item);
			model.fireTableDataChanged();
		}		
	}

	boolean isDuplicateEntry(DataListItem item) {
		int count = 0;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			DataListItem itm = list.get(i);
			if (itm.executable.equalsIgnoreCase(item.executable)) {
				if (count > 0) {
					return true;
				}
				count++;
			}
		}
		return false;
	}

	void removeData() {
		int[] indexes = table.getSelectedRows();
		if (indexes.length < 1) {
			JOptionPane.showMessageDialog(this, "No item selected");
			return;
		}
		DataListItem[] items = new DataListItem[indexes.length];
		for (int i = 0; i < indexes.length; i++) {
			DataListItem item = list.get(indexes[i]);
			items[i] = item;
		}
		for (int i = 0; i < indexes.length; i++) {
			DataListItem item = items[i];
			list.remove(item);
		}
		model.fireTableDataChanged();
	}
	
	void exitApp() {
		System.exit(0);
	}
	
	void execute() {
		int[] indexes = table.getSelectedRows();
		if (indexes.length > 0) {
			DataListItem[] items = new DataListItem[indexes.length];
			for (int i = 0; i < indexes.length; i++) {
				DataListItem item = list.get(indexes[i]);
				items[i] = item;
			}
			for (int i = 0; i < indexes.length; i++) {
				DataListItem item = items[i];
				String driverServer = item.executable;
				driverServer = Utility.checkDriverServer(driverServer);
				closeDriverServer(item, driverServer);
			}
		} else {
			int size = list.size();
			if (size < 1) {
				System.out.println("No driver to kill.");
				return;
			}
			for (int i = 0; i < size; i++) {
				DataListItem item = list.get(i);
				String driverServer = item.executable;
				driverServer = Utility.checkDriverServer(driverServer);
				closeDriverServer(item, driverServer);
			}
		}		
	}
	
	public void closeDriverServer(DataListItem item, String driverServer) {
		QuitProcess qp = QuitProcess.getInstance();
		qp.setDriverServer(driverServer);
		qp.quitAllDriverServers();
		if (qp.getExitCode() != 0) {
			item.status = driverServer + " is not running";
			model.fireTableDataChanged();
			//JOptionPane.showMessageDialog(this, driverServer + " is not running");
		} else {
			item.status = driverServer + " is successfully killed";
			model.fireTableDataChanged();
			//JOptionPane.showMessageDialog(this, driverServer + " is successfully killed");
		}
	}

}
