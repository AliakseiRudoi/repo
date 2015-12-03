package com.epam.rudoi.newsportal.restful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.service.INewsManagementService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_spring_test.xml")
public class TagRestIAPITest {

	@Test
	public void addAndDeleteTagTest() throws ClientProtocolException, IOException {
			
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		Tag expectedObject = new Tag("SOME_TAG_NAME");
		
		StringEntity entity = new StringEntity("{\"tagName\":\"SOME_TAG_NAME\"}");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/tags");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		Tag actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				Tag.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getTagName(), actualObject.getTagName());
		
		String expectedResponceStatusDel = "HTTP/1.1 204 No Content";
		
		HttpDelete requestDel = new HttpDelete("http://localhost:8085/news-web/tags/" + actualObject.getTagId());

		HttpResponse httpResponseDel = httpClient.execute(requestDel);
		
		assertEquals(expectedResponceStatusDel, httpResponseDel.getStatusLine().toString());
		
	}
	
	@Test 
	public void getTagsTest() throws ClientProtocolException, IOException {
		
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		List<Tag> expectedObject = new ArrayList<Tag>();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://localhost:8085/news-web/tags");
		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Tag> actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				ArrayList.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getClass(), actualObject.getClass());
		
	}
	
	@Test
	public void updateTagTest() throws ClientProtocolException, IOException {
		
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		Boolean expectedObject = true;
		
		StringEntity entity = new StringEntity("{\"tagName\":\"SOME_TAG_NAME\",\"tagId\":38}");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/tags/update");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);
		System.out.println(httpResponse.toString());
		
		ObjectMapper objectMapper = new ObjectMapper();
		Boolean actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				Boolean.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject, actualObject);
		
	}
	
}
