package com.simulation.core.util;

import javax.servlet.http.HttpServletRequest;

public class PageUtils {

	public static Integer getPage() {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		Integer currentPage = request.getParameter("offset") == null ? 0
				: Integer.parseInt(request.getParameter("offset"));
		Integer rows = getRows();
		if (currentPage != 0) {// 获取页数
			currentPage = currentPage / rows;
		}
		currentPage += 1;
		return currentPage;
	}

	public static Integer getRows() {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		Integer rows = request.getParameter("limit") == null ? 10 : Integer
				.parseInt(request.getParameter("limit"));
		return rows;
	}
}
