package com.epam.rudoi.newsportal.restful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.restful.impl.AuthorRestService;
import com.epam.rudoi.newsportal.service.INewsManagementService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_spring_test.xml")
public class AuthorRestTest {

	@Mock
	private INewsManagementService newsService;
	
	@InjectMocks
	@Autowired
	private AuthorRestService authorRestService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		assertNotNull(authorRestService);
	}
	
	@Test
	public void addAuthorTest() throws DAOExeption, ServiceExeption, RestException {
		
		Author expectedAuthor = new Author();
		Author resultAuthor = null;
		
		when(authorRestService.addItem(new Author())).thenReturn(expectedAuthor);
		resultAuthor = authorRestService.addItem(new Author());
		verify(newsService, times(1)).addAuthor(new NewsManagementVO(new Author()));
		assertEquals(expectedAuthor, resultAuthor);
	}
	
	@Test
	public void updateAuthorTest() throws DAOExeption, ServiceExeption, RestException {
		
		authorRestService.updateItem(new Author());
		verify(newsService, times(1)).editAuthor(new NewsManagementVO(new Author()));
	}
	
	@Test
	public void deleteAuthorTest() throws DAOExeption, ServiceExeption, RestException {
		authorRestService.deleteItem(anyLong());
		verify(newsService, times(1)).expiredAuthor(anyLong());
	}
	
	@Test
	public void getAuthorsTest() throws DAOExeption, ServiceExeption, RestException {
		List<Author> expectedAuthorsList = new ArrayList<Author>();
		List<Author> resultAuthorsList = null;
		
		when(authorRestService.getAuthors()).thenReturn(expectedAuthorsList);
		resultAuthorsList = authorRestService.getAuthors();
		verify(newsService, times(1)).getAuthorsList();
		assertEquals(expectedAuthorsList, resultAuthorsList);
	}
	
}
