package com.epam.rudoi.newsportal.restful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.restful.impl.TagRestService;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import static org.mockito.Matchers.anyLong;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context_spring_test.xml")
public class TagRestTest {

	@Mock
	private INewsManagementService newsService;
	
	@InjectMocks
	@Autowired
	private TagRestService tagRestService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		assertNotNull(tagRestService);
	}

	@Test
	public void addTagTest() throws DAOExeption, ServiceExeption, RestException {
		Tag expectedTag = new Tag();
		Tag resultTag = null;
		when(tagRestService.addItem(new Tag())).thenReturn(new Tag());
		
		resultTag = tagRestService.addItem(new Tag());
		
		verify(newsService, times(1)).addTag(new NewsManagementVO(new Tag()));
		assertEquals(expectedTag, resultTag);
	}
	
	@Test
	public void updateTagTest() throws DAOExeption, ServiceExeption, RestException {
		tagRestService.updateItem(new Tag());
		verify(newsService, times(1)).editTag(new NewsManagementVO(new Tag()));
	}
	
	@Test
	public void deleteTagTest() throws DAOExeption, ServiceExeption, RestException {
		tagRestService.deleteItem(anyLong());
		verify(newsService, times(1)).deleteTag(anyLong());
	}

	@Test
	public void getTagsTest() throws DAOExeption, ServiceExeption, RestException {
		List<Tag> expectedTagsList = new ArrayList<Tag>();
		List<Tag> resultTagsList = null;
		when(tagRestService.getTags()).thenReturn(expectedTagsList);
		resultTagsList = tagRestService.getTags();
		assertEquals(expectedTagsList, resultTagsList);
		verify(newsService, times(1)).getTagsList();
	}
	
}
