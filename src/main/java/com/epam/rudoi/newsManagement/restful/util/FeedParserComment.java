package com.epam.rudoi.newsManagement.restful.util;

import static com.epam.rudoi.newsManagement.constants.Expression.EXPRESSION_FOR_PARSE_FEED_NEWS_DATE;
import static com.epam.rudoi.newsManagement.constants.Expression.SIMPLE_DATE_FORMAT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.epam.rudoi.newsportal.entity.Comment;

public class FeedParserComment {

	public static List<Comment> parseFeedComment(String feedJson) {
		Comment comment = new Comment();
		List<Comment> commentsList = new ArrayList<Comment>();
		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = null;
		try {
			jParser = jfactory.createJsonParser(feedJson);

			while (jParser.nextToken() != JsonToken.END_OBJECT) {

				String fieldname = jParser.getCurrentName();
				if ("message".equals(fieldname)) {
					jParser.nextToken();
					comment.setCommentText(jParser.getText());
					System.out.println(comment.getCommentText());
				}
				if ("created_time".equals(fieldname)) {
					jParser.nextToken();
					comment.setCreationDate(dateBuilder(dateStringBuilder(jParser.getText())));
					System.out.println(comment.getCreationDate());

				}

			}

		} catch (IOException e1) {

			e1.printStackTrace();
		}
		return commentsList;
	}

	private static Date dateBuilder(String feedFieldForParseToDate) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		try {
			date = formatter.parse(feedFieldForParseToDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	private static String dateStringBuilder(String feedFieldForParseToCorrectStringDate) {
		String dateString = null;

		Pattern pattern = Pattern.compile(EXPRESSION_FOR_PARSE_FEED_NEWS_DATE);
		Matcher matcher = pattern.matcher(feedFieldForParseToCorrectStringDate);
		matcher.find();
		dateString = matcher.group().toString();
		return dateString;
	}

}
