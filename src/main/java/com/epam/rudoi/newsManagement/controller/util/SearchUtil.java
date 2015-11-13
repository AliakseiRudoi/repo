package com.epam.rudoi.newsManagement.controller.util;

public class SearchUtil {

	public static final long NEWS_ON_PAGE = 10;
	public static final long CURRENT_NEWS_INCRIMENT = 1;
	
	public static Long countUpStartIndex(Long currentPageNumber) {
		return currentPageNumber * NEWS_ON_PAGE - NEWS_ON_PAGE + CURRENT_NEWS_INCRIMENT;
	}

	public static Long countUpEndtIndex(Long currentPageNumber) {
		return currentPageNumber * NEWS_ON_PAGE;
	}

}
