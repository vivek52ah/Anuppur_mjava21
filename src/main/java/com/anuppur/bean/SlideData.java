package com.anuppur.bean;

import java.util.List;

public class SlideData {
	
	
	   private String title;
	    private List<Object[]> tableData;
	   private List<String> headers;
	   private List<Double> total;
	   private List<String> titles;
	   private int slideCount;
	   private List<String> subHeaders;
	   
        

		public List<Double> getTotal() {
		return total;
	}

	public void setTotal(List<Double> total) {
		this.total = total;
	}

		public List<String> getSubHeaders() {
		return subHeaders;
	}

	public void setSubHeaders(List<String> subHeaders) {
		this.subHeaders = subHeaders;
	}

		public int getSlideCount() {
		return slideCount;
	}

	public void setSlideCount(int slideCount) {
		this.slideCount = slideCount;
	}

		public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

		public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

		public SlideData() {
			// TODO Auto-generated constructor stub
		}

		

		

		public SlideData(String title, List<Object[]> tableData, int slideCount) {
			super();
			this.title = title;
			this.tableData = tableData;
			this.slideCount = slideCount;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public List<Object[]> getTableData() {
			return tableData;
		}

		public void setTableData(List<Object[]> tableData) {
			this.tableData = tableData;
		}

		
	    

}
