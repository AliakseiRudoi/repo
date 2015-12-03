package com.epam.rudoi.newsportal.restful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyLong;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.epam.rudoi.newsportal.restful.impl.TagRestService;
import com.epam.rudoi.newsportal.service.INewsManagementService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_spring_test.xml")
public class NewsRestTest {

	@Mock
	private INewsManagementService serviceManager;
	
	@Mock
	private NewsManagementVO newsVO;
	
	@InjectMocks
	@Autowired
	private INewsRest newsRest;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		assertNotNull(newsRest);
		assertNotNull(serviceManager);
	}

	@Test
	public void addNewsTest() throws DAOExeption, ServiceExeption, RestException {
		NewsManagementVO newsManagementVO = new NewsManagementVO(new News());
		News expectedNews = new News();
		News resultNews = null;
		when(newsRest.addNews(newsManagementVO)).thenReturn(new News());
		newsRest.addNews(newsManagementVO);
		verify(serviceManager, times(1)).addNews(newsManagementVO);
		assertEquals(expectedNews, resultNews);
		
	}
	
	@Test
	public void updateNewsTest() throws DAOExeption, ServiceExeption, RestException {
		NewsManagementVO newsManagementVO = new NewsManagementVO(new News());
		Boolean expectedBoolean = true;
		Boolean resultBoolean = false;
		
		when(newsRest.updateNews(newsManagementVO)).thenReturn(expectedBoolean);
		resultBoolean = newsRest.updateNews(newsManagementVO);
		verify(serviceManager, times(1)).editNews(newsManagementVO);
		assertEquals(expectedBoolean, resultBoolean);
		
	}
	
	@Test
	public void deleteNewsTest() throws DAOExeption, ServiceExeption, RestException, ServiceExeption {
		NewsManagementVO newsManagementVO = new NewsManagementVO(new News());
		
		newsRest.deleteNews(newsManagementVO);
		verify(serviceManager, times(1)).deleteNews(newsManagementVO);
		
	}

	@Test
	public void getNewsTest() throws RestException, ServiceExeption {
		NewsManagementVO newsManagementVO = new NewsManagementVO(new News());
		List<NewsManagementVO> expectedNewsList = new ArrayList<NewsManagementVO>();
		List<NewsManagementVO> resultNewsList = null;
		
		when(newsRest.getNews()).thenReturn(new ArrayList<NewsManagementVO>());
		resultNewsList = newsRest.getNews();
		verify(serviceManager, times(1)).searchNews(new SearchCriteria(new Author()), 1L, 10L);
		assertEquals(expectedNewsList, resultNewsList);
		
	}
	
	@Test
	public void getSingleNewsTest() throws RestException, ServiceExeption {
		
		NewsManagementVO expectedNews = new NewsManagementVO();
		NewsManagementVO resultNews = null;
		
		when(newsRest.getSingleNews(anyLong())).thenReturn(expectedNews);
		resultNews = newsRest.getSingleNews(anyLong());
		verify(serviceManager, times(1)).showSinglNews(anyLong());
		assertEquals(expectedNews, resultNews);
		
	}
	
	@Test
	public void getAuthorsTest() throws RestException, ServiceExeption {
		
		List<Author> expectedAuthorsList = new ArrayList<Author>();
		List<Author> resultAuthorsList = null;
		
		when(newsRest.getAuthors()).thenReturn(expectedAuthorsList);
		resultAuthorsList = newsRest.getAuthors();
		verify(serviceManager, times(1)).getAuthorsList();
		assertEquals(expectedAuthorsList, resultAuthorsList);
		
	}
	
	@Test 
	public void getTagsTest() throws RestException, ServiceExeption {
		
		List<Tag> expectedTagsList = new ArrayList<Tag>();
		List<Tag> resultTagsList = null;
		
		when(newsRest.getTags()).thenReturn(expectedTagsList);
		resultTagsList = newsRest.getTags();
		verify(serviceManager, times(1)).getTagsList();
		assertEquals(expectedTagsList, resultTagsList);
		
	}
	
	@Test
	public void searchNewsTest() throws RestException, ServiceExeption {
		SearchCriteria searchCriteria = new SearchCriteria(new Author());
		
		List<NewsManagementVO> expectedNewsManagementVOList = new ArrayList<NewsManagementVO>();
		List<NewsManagementVO> resultNewsManagementVOList = null;
		
		when(newsRest.searchNews(searchCriteria)).thenReturn(expectedNewsManagementVOList);
		resultNewsManagementVOList = newsRest.searchNews(searchCriteria);
		verify(serviceManager, times(1)).searchNews(searchCriteria, 1L, 10L);
		assertEquals(expectedNewsManagementVOList, resultNewsManagementVOList);
	}
	
	@Test
	public void getCountNewsTest() throws RestException, ServiceExeption {
		SearchCriteria searchCriteria = new SearchCriteria(new Author());
		
		newsRest.getCountNews(searchCriteria);
		verify(serviceManager, times(1)).countSearchNews(searchCriteria);
		
	}
	
	@Test
	public void getCommentsTest() throws RestException, ServiceExeption {
		
		List<Comment> expectedCommentsList = new ArrayList<Comment>();
		List<Comment> resultCommentsList = null;
		
		when(newsRest.getComments(anyLong())).thenReturn(expectedCommentsList);
		resultCommentsList = newsRest.getComments(anyLong());
		verify(serviceManager, times(1)).getCommentsList(anyLong());
		assertEquals(expectedCommentsList, resultCommentsList);
		
	}
	
	@Test
	public void deleteCommentTest() throws ServiceExeption, RestException {
		
		newsRest.deleteComment(anyLong());
		verify(serviceManager, times(1)).deleteComments(anyLong());
		
	}
	
	@Test
	public void addCommentTest() throws RestException, ServiceExeption {

		Comment comment = new Comment();
		comment.setCreationDate(new Date());
		
		Comment expectedComment = new Comment();
		Comment resultComment = null;
		
		when(newsRest.addComment(comment)).thenReturn(expectedComment);
		resultComment = newsRest.addComment(comment);
		verify(serviceManager, times(1)).addComment(comment);
		assertEquals(expectedComment.getCommentText(), resultComment.getCommentText());
		
	}
	
}
