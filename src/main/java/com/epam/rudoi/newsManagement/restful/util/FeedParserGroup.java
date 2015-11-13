package com.epam.rudoi.newsManagement.restful.util;

import static com.epam.rudoi.newsManagement.constants.Expression.*;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

public class FeedParserGroup {

	public List<NewsManagementVO> parseFeedGpoup(String feedJson) {

		List<NewsManagementVO> newsVOList = new ArrayList<NewsManagementVO>();

		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = null;
		try {
			jParser = jfactory.createJsonParser(feedJson);

			while (jParser.nextToken() != JsonToken.END_ARRAY) {
				News news = null;
				NewsManagementVO newsManagementVO = null;
				if (jParser.getCurrentToken().equals(JsonToken.START_OBJECT)) {
					news = new News();
					newsManagementVO = new NewsManagementVO();
				}

				while (jParser.nextToken() != JsonToken.END_OBJECT) {

					String fieldname = jParser.getCurrentName();
					if ("message".equals(fieldname)) {
						jParser.nextToken();
						news.setNewsTitle(titleBuilder(jParser.getText()));
						news.setNewsShortText(shortTextBuilder(jParser.getText()));
						news.setNewsFullText(jParser.getText());
					}
					if ("created_time".equals(fieldname)) {
						jParser.nextToken();
						news.setNewsCreationDate(dateBuilder(dateStringBuilder(jParser.getText())));
						news.setNewsModificationDate(dateBuilder(dateStringBuilder(jParser.getText())));
						
					}
					if ("id".equals(fieldname)) {
						jParser.nextToken();
						news.setFacebookId(jParser.getText());
						news.setVersion(1L);

						newsManagementVO.setNews(news);
						newsVOList.add(newsManagementVO);
					}
				}

			}

		} catch (Exception e1) {

			e1.printStackTrace();
		}
		return newsVOList;

	}

	private Date dateBuilder(String feedFieldForParseToDate) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		try {
			date = formatter.parse(feedFieldForParseToDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private String dateStringBuilder(String feedFieldForParseToCorrectStringDate) {
		String dateString = null;

		Pattern pattern = Pattern.compile(EXPRESSION_FOR_PARSE_FEED_NEWS_DATE);
		Matcher matcher = pattern.matcher(feedFieldForParseToCorrectStringDate);
		matcher.find();
		dateString = matcher.group().toString();
		return dateString;
	}

	private String titleBuilder(String feedFieldForExtractTitle) {
		String title = null;

		Pattern pattern = Pattern.compile(EXPRESSION_FOR_PARSE_FEED_NEWS_TITLE);
		Matcher matcher = pattern.matcher(feedFieldForExtractTitle);
		matcher.find();
		title = matcher.group(1).toString();
		return title;
	}

	private String shortTextBuilder(String feedFieldForExtractTitle) {
		String shortText = null;

		Pattern pattern = Pattern.compile(EXPRESSION_FOR_PARSE_FEED_NEWS_SHORTTEXT);
		Matcher matcher = pattern.matcher(feedFieldForExtractTitle);
		matcher.find();
		shortText = matcher.group(1).toString();
		return shortText;
	}

}
