package com.epam.rudoi.newsManagement.restful;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.Comment;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/news1")
public class NewsRestService {

	@Autowired
	private INewsManagementService serviceManager;

	@POST
	@Path("/addCommentAdmin") /* "addComment" */
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComment(Comment comment) throws ServiceExeption {

		comment.setCreationDate(new Date());
		serviceManager.addComment(comment);

	}

	@DELETE
	@Path("/deleteCommentAdmin") /* "deleteComment" */
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteComment(Comment comment) throws ServiceExeption {

		serviceManager.deleteComments(comment.getCommentId());

	}

	@POST
	@Path("/updateNews") /* "updateNews" */
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateNews(NewsManagementVO newsVO) throws ServiceExeption {

		serviceManager.editNews(updateNewsVOBuilder(newsVO));

	}
	
	@POST
	@Path("/addNewsAction") /*"addNews"*/
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNews(NewsManagementVO newsVO) throws ServiceExeption {
		
		serviceManager.addNews(addNewsVOBuilder(newsVO));
		
	}
	
	@GET
	@Path("/{newsId}") /*"showNews"*/
	@Produces(MediaType.APPLICATION_JSON)
	public NewsManagementVO showSingleNews (@PathParam("newsId") Long newsId) throws ServiceExeption {
	
		return serviceManager.showSinglNews(newsId);
		
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteNews(NewsManagementVO newsVO1)  throws ServiceExeption {
		
		serviceManager.deleteNews(newsVO1);
		
	}
	

	private NewsManagementVO addNewsVOBuilder(NewsManagementVO newsVO) throws ServiceExeption {
		List<Author> authorsList = new ArrayList<Author>();
		Author author = serviceManager.readAuthor(newsVO);
		authorsList.add(author);

		List<Tag> tagsList = new ArrayList<Tag>(newsVO.getTagsIdList().size());
		List<Long> tagsIdList = newsVO.getTagsIdList();
		for (Long long1 : tagsIdList) {
			Tag tag = new Tag();
			tag.setTagId(long1);
			tagsList.add(tag);
		}
		
		newsVO.getNews().setVersion(1L);
		newsVO.getNews().setAuthorsList(authorsList);
		newsVO.getNews().setTagsList(tagsList);
		newsVO.getNews().setNewsCreationDate(new Date());
		newsVO.getNews().setNewsModificationDate(new Date());
		
		return newsVO;
	}

	private NewsManagementVO updateNewsVOBuilder(NewsManagementVO newsVO) {
		newsVO.getNews().setNewsCreationDate(new Date());
		newsVO.getNews().setNewsModificationDate(new Date());
		Author author = newsVO.getAuthor();
		List<Author> authorsList = new ArrayList<Author>();
		authorsList.add(author);
		List<Tag> tagsList = newsVO.getTagsList();
		newsVO.getNews().setAuthorsList(authorsList);
		newsVO.getNews().setTagsList(tagsList);

		return newsVO;
	}

}
