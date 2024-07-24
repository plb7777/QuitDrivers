package app.ui;

import java.io.Serializable;
import java.util.ArrayList;

public class DataList implements Serializable {

	private static final long serialVersionUID = 1L;

	ArrayList<DataListItem> list = new ArrayList<>();

	DataListItem get(int index) {
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			DataListItem item = (DataListItem) list.get(i);
			if (k == index)
				return item;
			k++;
		}
		return null;
	}

	int getIndex(DataListItem item) {
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			DataListItem itm = (DataListItem) list.get(i);
			if (item.equals(itm)) {
				return k;
			}
			k++;
		}
		return -1;
	}

	void remove(DataListItem item) {
		list.remove(item);
	}

	void remove(int index) {
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			if (k == index)
				list.remove(i);
			k++;
		}
	}

	void add(DataListItem item) {
		list.add(item);
	}

	int size() {
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			k++;
		}
		return k;
	}

}
