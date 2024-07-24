package app.ui;

import javax.swing.table.AbstractTableModel;

public class MainTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	final String[] cols = { "", "Name", "Executable", "Status" };
	DataList list = null;

	public void setList(DataList list) {
		this.list = list;
		fireTableDataChanged();
	}

	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	public String getColumnName(int col) {
		return cols[col];
	}

	@Override
	public int getRowCount() {
		if (list == null)
			return 0;
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		DataListItem item = list.get(row);
		switch (col) {
		case 0:
			return Integer.valueOf(row + 1);
		case 1:
			return item.name;
		case 2:
			return item.executable;
		case 3:
			return item.status;
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public void setValueAt(Object val, int row, int col) {
		DataListItem item = list.get(row);
		switch (col) {
		case 1:
			item.name = (String) val;
			break;
		case 2:
			item.executable = (String) val;
			break;
		}
		item.status = "Value changed";
		fireTableDataChanged();
	}

}
