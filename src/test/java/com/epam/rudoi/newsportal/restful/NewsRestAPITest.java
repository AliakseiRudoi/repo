package com.epam.rudoi.newsportal.restful;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.Comment;
import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.restful.impl.NewsRestService;
import com.epam.rudoi.newsportal.restful.impl.TagRestService;
import com.epam.rudoi.newsportal.service.ICommentService;
import com.epam.rudoi.newsportal.service.INewsManagementService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpProcessor;
import org.codehaus.jackson.map.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_spring_test.xml")
public class NewsRestAPITest {

	@Test
	public void addAndDeleteNewsTest() throws ClientProtocolException, IOException {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		News expectedObject = new News("Google has asked the", "Google has asked the FCC for", "Google has asked the FCC for a license to test experimental radios that use millimetre bandwidth for wireless transmission in the US, suggesting at a trial run of Project Loon.");
		
		StringEntity entity = new StringEntity("{\"comment\":null,\"tag\":null,\"author\":{\"expider\":null,\"authorName\":\"Android Authority\",\"authorId\":45},\"news\":{\"version\":1,\"author\":null,\"tagsList\":[{\"tagName\":\"Android Authority\",\"tagId\":25}],\"commentsList\":[],\"authorsList\":[{\"expider\":null,\"authorName\":\"Android Authority\",\"authorId\":45}],\"newsModificationDate\":\"2015-11-27\",\"newsCommentAmmount\":null,\"newsFullText\":\"Google has asked the FCC for a license to test experimental radios that use millimetre bandwidth for wireless transmission in the US, suggesting at a trial run of Project Loon.\",\"facebookId\":\"155608091155587_917445624971826\",\"newsTitle\":\"Google has asked the\",\"newsCount\":null,\"newsShortText\":\"Google has asked the FCC for\",\"authorId\":null,\"newsId\":4961,\"tagsIdList\":null,\"newsCreationDate\":1448615256000},\"tagsList\":[{\"tagName\":\"Android Authority\",\"tagId\":25}],\"commentsList\":[],\"authorsList\":null,\"searchCriteria\":null,\"newsList\":null,\"tagsIdList\":null,\"newsIdList\":null}");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/news");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);
		
		ObjectMapper objectMapper = new ObjectMapper();
		News actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				News.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getNewsTitle(), actualObject.getNewsTitle());
		
		String expectedResponceStatusDel = "HTTP/1.1 204 No Content";
		
		StringEntity entityDel = new StringEntity("{\"newsIdList\":["+ actualObject.getNewsId() +"]}");
		entityDel.setContentType("application/json");
		
		HttpPost requestDel = new HttpPost("http://localhost:8085/news-web/news/delete");
		requestDel.setEntity(entityDel);

		HttpResponse httpResponseDel = httpClient.execute(requestDel);
		
		assertEquals(expectedResponceStatusDel, httpResponseDel.getStatusLine().toString());
		
	}

	@Test
	public void updateNewsTest() throws ClientProtocolException, IOException {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		Boolean expectedObject = true;
		
		StringEntity entity = new StringEntity("{\"comment\":null,\"tag\":null,\"author\":{\"expider\":null,\"authorName\":\"Android Authority\",\"authorId\":45},\"news\":{\"version\":1,\"author\":null,\"tagsList\":[{\"tagName\":\"Android Authority\",\"tagId\":25}],\"commentsList\":[],\"authorsList\":[{\"expider\":null,\"authorName\":\"Android Authority\",\"authorId\":45}],\"newsModificationDate\":\"2015-11-27\",\"newsCommentAmmount\":null,\"newsFullText\":\"Google has asked the FCC for a license to test experimental radios that use millimetre bandwidth for wireless transmission in the US, suggesting at a trial run of Project Loon.\",\"facebookId\":\"155608091155587_917445624971826\",\"newsTitle\":\"Google has asked the\",\"newsCount\":null,\"newsShortText\":\"Google has asked the FCC for\",\"authorId\":null,\"newsId\":4961,\"tagsIdList\":null,\"newsCreationDate\":1448615256000},\"tagsList\":[{\"tagName\":\"Android Authority\",\"tagId\":25}],\"commentsList\":[],\"authorsList\":null,\"searchCriteria\":null,\"newsList\":null,\"tagsIdList\":null,\"newsIdList\":null}");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/news/update");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Boolean actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				Boolean.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject, actualObject);
		
	}

	@Test
	public void getNewsTest() throws ClientProtocolException, IOException {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		List<NewsManagementVO> expectedObject = new ArrayList<NewsManagementVO>();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://localhost:8085/news-web/news");
		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		List<NewsManagementVO> actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				ArrayList.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getClass(), actualObject.getClass());
		
	}
	
	@Test
	public void showShingleNewsTest() throws ClientProtocolException, IOException {
		String newsId = "4954";
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		NewsManagementVO expectedObject = new NewsManagementVO(new News(4954L));

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://localhost:8085/news-web/news/" + newsId);
		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		NewsManagementVO actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				NewsManagementVO.class);

		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getNews().getNewsId(), actualObject.getNews().getNewsId());

	}
	
	@Test
	public void getAuthorsTest() throws ClientProtocolException, IOException {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		List<NewsManagementVO> expectedObject = new ArrayList<NewsManagementVO>();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://localhost:8085/news-web/news/getAuthors");
		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Author> actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				ArrayList.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getClass(), actualObject.getClass());
	}
	
	@Test
	public void getTagsTest() throws ClientProtocolException, IOException {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		List<NewsManagementVO> expectedObject = new ArrayList<NewsManagementVO>();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://localhost:8085/news-web/news/getAuthors");
		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Tag> actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				ArrayList.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getClass(), actualObject.getClass());
	}
	
	@Test
	public void getCommentsTest() throws ClientProtocolException, IOException {
		String newsId = "4954";
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		List<NewsManagementVO> expectedObject = new ArrayList<NewsManagementVO>();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet("http://localhost:8085/news-web/news/getComments/"+newsId);
		HttpResponse httpResponse = httpClient.execute(request);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Tag> actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				ArrayList.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getClass(), actualObject.getClass());
	}
	
	@Test
	public void addAndDeleteCommentTest() throws ClientProtocolException, IOException, ServiceExeption {
		//Add comment test
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		Comment expectedObject = new Comment(4954L, "some comment");
		StringEntity entity = new StringEntity("{\"commentText\":\"some comment\",\"creationDate\":null,\"commentId\":null,\"newsId\":4954}");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/news/comment");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Comment actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				Comment.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject.getCommentText(), actualObject.getCommentText());

		//Delete comment test
		Long commentId = actualObject.getCommentId();
		String expectedResponceStatusDel = "HTTP/1.1 204 No Content";
		
		HttpUriRequest requestDel = new HttpDelete("http://localhost:8085/news-web/news/comment/"+commentId); 
		HttpResponse httpResponseDel = httpClient.execute(requestDel);

		assertEquals(expectedResponceStatusDel, httpResponseDel.getStatusLine().toString());
		
	}
	
	@Test
	public void searchNewsTest() throws ClientProtocolException, IOException {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		List<NewsManagementVO> expectedObject = new ArrayList<NewsManagementVO>();
		StringEntity entity = new StringEntity("{\"author\" : { \"authorId\" : null } , \"tagsIdList\" : [ null ], \"pageNumber\" : null, \"currentPage\" : null }");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/news/search");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<NewsManagementVO> actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				ArrayList.class);
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject, actualObject);
	}

	@Test
	public void getCountNewsTest() throws ClientProtocolException, IOException, ServiceExeption {
		String expectedResponceStatus = "HTTP/1.1 200 OK";
		String expectedHeader = "Content-Type: application/json";
		SearchCriteria expectedObject = new SearchCriteria(new Author());
		
		StringEntity entity = new StringEntity("{\"author\" : { \"authorId\" : null } , \"tagsIdList\" : null, \"pageNumber\" : null, \"currentPage\" : null }");
		entity.setContentType("application/json");
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8085/news-web/news/getNewsCount");
		request.setEntity(entity);

		HttpResponse httpResponse = httpClient.execute(request);
		
		ObjectMapper objectMapper = new ObjectMapper();
		SearchCriteria actualObject = objectMapper.readValue(httpResponse.getEntity().getContent(),
				SearchCriteria.class);
		
		expectedObject.setPageNumber(actualObject.getPageNumber());
		
		assertEquals(expectedResponceStatus, httpResponse.getStatusLine().toString());
		assertEquals(expectedHeader, httpResponse.getFirstHeader("Content-Type").toString());
		assertEquals(expectedObject, actualObject);
	}
	
}
