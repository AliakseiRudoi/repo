package com.epam.rudoi.newsportal.restful.impl;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.Comment;
import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.restful.INewsRest;
import com.epam.rudoi.newsportal.service.INewsManagementService;
import com.epam.rudoi.newsportal.service.scheduler.util.SearchNewsUtil;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/news")
public class NewsRestService implements INewsRest{

	private static final int INDEX_PAGINATION = 1;
	private static final int NEWS_ON_PAGE = 10;
	
	@Autowired
	private INewsManagementService serviceManager;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public News addNews (NewsManagementVO newsManagementVO) throws ServiceExeption {

		Author author = serviceManager.readAuthorByName(newsManagementVO);
		List<Author> authorsList = new ArrayList<Author>();
		authorsList.add(author);
		newsManagementVO.getNews().setAuthorsList(authorsList);
		newsManagementVO.getNews().setNewsModificationDate(new Date());
		newsManagementVO.getNews().setNewsCreationDate(new Date());
		newsManagementVO.getNews().setVersion(1L);
		
		Long newsId = serviceManager.addNews(newsManagementVO);
		return serviceManager.readNews(newsId);
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Boolean updateNews (NewsManagementVO newsManagementVO) throws ServiceExeption { 
		Boolean flag = true;
		Author author = serviceManager.readAuthorByName(newsManagementVO);
		List<Author> authorsList = new ArrayList<Author>();
		authorsList.add(author);
		newsManagementVO.getNews().setAuthorsList(authorsList);
		
		boolean allowToUpdateCurrentNews = serviceManager.editNews(newsManagementVO);
		if (!allowToUpdateCurrentNews) {
			flag = false;
		}
		return flag;
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<NewsManagementVO> getNews() throws ServiceExeption {

		Long pageNumber = 1L;
		Long startIndex = SearchNewsUtil.countUpStartIndex(pageNumber);
		Long endIndex = SearchNewsUtil.countUpEndtIndex(pageNumber);
				
		SearchCriteria searchCriteria = fillSearchCriteria();

		return serviceManager.searchNews(searchCriteria, startIndex, endIndex);
	}
	
	@GET
	@Path("/{id}") 
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public NewsManagementVO getSingleNews(@PathParam("id") Long newsId) throws ServiceExeption {
		return serviceManager.showSinglNews(newsId);
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public void deleteNews(NewsManagementVO newsManagementVO) throws ServiceExeption {
		
		serviceManager.deleteNews(newsManagementVO);
		
	}
	
	@GET
	@Path("/getAuthors")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Author> getAuthors() throws ServiceExeption {
		return serviceManager.getAuthorsList();
	}
	
	@GET
	@Path("/getTags")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Tag> getTags() throws ServiceExeption {
		return serviceManager.getTagsList();
	}
	
	@GET
	@Path("/getComments/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Comment> getComments(@PathParam("id") Long newsId) throws ServiceExeption {
		return serviceManager.getCommentsList(newsId);
	}
	
	@DELETE
	@Path("/comment/{id}") 
	@Override
	public void deleteComment(@PathParam("id") Long commentId) throws ServiceExeption {
		serviceManager.deleteComments(commentId);
	}
	
	@POST
	@Path("/comment") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Comment addComment(Comment comment) throws ServiceExeption {
		comment.setCreationDate(new Date());
		Long commentId = serviceManager.addComment(comment);
		return serviceManager.getComment(commentId);
	}
	
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<NewsManagementVO> searchNews(SearchCriteria searchCriteria) throws ServiceExeption {
		
		Long pageNumber = 1L;
		Long startIndex = SearchNewsUtil.countUpStartIndex(pageNumber);
		Long endIndex = SearchNewsUtil.countUpEndtIndex(pageNumber);
				
		return serviceManager.searchNews(searchCriteria, startIndex, endIndex);
		
	}
	
	@POST
	@Path("/getNewsCount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public SearchCriteria getCountNews(SearchCriteria searchCriteria) throws ServiceExeption {
		if(searchCriteria.getAuthor() == null) {
		searchCriteria = fillSearchCriteria();
		}
		searchCriteria.setPageNumber(getPuginNumber(searchCriteria));
		return searchCriteria;
	}
	
	@POST
	@Path("/search/pagin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<NewsManagementVO> ajaxSearchNews(SearchCriteria searchCriteria) throws ServiceExeption {
		
		Long startIndex = SearchNewsUtil.countUpStartIndex(searchCriteria.getCurrentPage());
		Long endIndex = SearchNewsUtil.countUpEndtIndex(searchCriteria.getCurrentPage());
		
		if(searchCriteria.getAuthor() == null) {
			searchCriteria = fillSearchCriteria();
			}
		
		return serviceManager.searchNews(searchCriteria, startIndex, endIndex);
		
	}
	
	private SearchCriteria fillSearchCriteria() {
		Author author = new Author();
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setAuthor(author);
		return searchCriteria;
	}
	
	private Long getPuginNumber(SearchCriteria searchCriteria) throws ServiceExeption {
		Long newsCount = serviceManager.countSearchNews(searchCriteria);
		Long pnum = (newsCount - INDEX_PAGINATION) / NEWS_ON_PAGE + INDEX_PAGINATION;
		return pnum;
	}
	
}






