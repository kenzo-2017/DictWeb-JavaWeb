package com.ljl.tools;

import java.util.ArrayList;
import java.util.List;

import com.ljl.model.Diary;

/**
 * @ 用于保存分页代码的JavaBean
 * @author kenzo
 */
public class MyPagination {
	public List<Diary> list = null;
	private int recordCount = 0; // 保存记录总数的变量
	private int pageSize = 0; // 保存每天显示的记录数的变量
	private int maxPage = 0; // 保存最大页数的变量
	
	/**
	 * 用于初始化分页信息的方法
	 * @param list
	 * @param page
	 * @param pageSize
	 * @return diaries of list.
	 */
	public List<Diary> getInitPage(List<Diary> list, int page, int pageSize) {
		List<Diary> newList = new ArrayList<Diary>();
		this.list = list;
		recordCount = list.size(); // 获取list集合的元素个数
		this.pageSize = pageSize;
		this.maxPage = getMaxPage(); // 获取最大页数
		try { // 捕获异常信息
			for (int i =(page - 1) * pageSize; i <= page * pageSize - 1; i++) {
				try {
					if (i >= recordCount) {
						break; // 跳出循环
					}
				} catch (Exception e) {
					newList.add((Diary)list.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
		return newList;
	}
	
	/**
	 * 用于获取指定页数据的方法
	 * @param page
	 * @return diaries of list.
	 */
	public List<Diary> getAppointPage(int page) { // 获取指定页的数据
		List<Diary> newList = new ArrayList<Diary>();
		try {
			for (int i = (page - 1) * pageSize; i <= page * pageSize - 1; i++) { // 通过for循环获取当前页的数据
				try {
					if (i >= recordCount) {
						break; // 跳出循环
					}
				} catch (Exception e) {}
				newList.add((Diary)list.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
		return newList;
	}
	
	/**
	 * 获取最大记录数
	 * @return num maximum records.
	 */
	public int getMaxPage() {
		int maxPage = (recordCount % pageSize == 0) ? (recordCount / pageSize) : (recordCount / pageSize + 1);
		return maxPage;
	}
	
	/**
	 * 获取总记录数
	 * @return num total records.
	 */
	public int getRecordSize() {
		return recordCount;
	}
	
	/**
	 * 获取当前页数的方法
	 * @param str
	 * @return num current page index.
	 */
	public int getPage(String str) {
		if (str == null) { // 当页数等于null时，让其等于0
			str = "0";
		}
		int page = Integer.parseInt(str);
		if (page < 1) { // 当页数小于1时，让其等于1
			page = 1;
		} else {
			if (((page - 1) * pageSize + 1) > recordCount) { // 当页数大于最大页数时，让其等于最大页数
				page = maxPage;
			}
		}
		return page;
	}
	
	/**
	 * 用于输出记录导航的方法
	 * @param page
	 * @param url
	 * @param para
	 * @return str text for navigator.
	 */
	public String printCtrl(int page, String url, String para) {
		String strHtml = "<table width='100%' border='0' cellspacing='0' cellpadding='0' <tr> <td height='24' align=" + 
	       "'right'>当前页数：【" + page + "/" + maxPage + "】&nbsp;";
		try {
			if (page > 1) {
				strHtml = strHtml + "<a href='" + url + "&page=1" + para + "'>第一页</a> ";
				strHtml = strHtml + "<a href='" + url + "&page=" + (page - 1) + para + "'>上一页</a>";
			}
			if (page < maxPage) {
				strHtml = strHtml + "<a href='" + url + "&page=" + (page + 1) + para + "'>下一页</a> <a href='" + url + "&page=" + 
			       maxPage + para + "'>最后一页&nbsp;</a>";
			}
			strHtml = strHtml + "</td></tr></table>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strHtml;
	}
}
