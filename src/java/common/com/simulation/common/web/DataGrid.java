package com.simulation.common.web;

import java.util.ArrayList;
import java.util.List;

/**
 * dataGrid返回格式
 */
public class DataGrid {

	
	private int total = 0;
	
	
	private List rows = new ArrayList();
	
	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
