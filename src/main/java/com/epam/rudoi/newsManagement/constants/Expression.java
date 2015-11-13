package com.epam.rudoi.newsManagement.constants;

public class Expression {
    	
    private Expression() {
	
    };
    
    	public final static String EXPRESSION_FOR_TAGS_AUTHORS_TITLE = "[\\w\\W]{3,30}$"; 
    	public final static String EXPRESSION_FOR_SHORTTEXT_COMMENTS = "[\\w\\W]{3,100}$";
    	public final static String EXPRESSION_FOR_FULLTEXT = "[\\w\\W]{3,2000}$";
    	
    	public final static String EXPRESSION_FOR_PARSE_FEED_NEWS_TITLE = "^((?:\\S+\\s+){3}\\S+)";
    	public final static String EXPRESSION_FOR_PARSE_FEED_NEWS_SHORTTEXT = "^((?:\\S+\\s+){5}\\S+)";
    	//public final static String EXPRESSION_FOR_PARSE_FEED_NEWS_DATE = ".+?(?=T)";
    	public final static String EXPRESSION_FOR_PARSE_FEED_NEWS_DATE = "^[^+]*";
    	
    	public final static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    	
}
