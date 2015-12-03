package com.epam.rudoi.newsportal.restful.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.restful.IAuthorRest;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/authors")
public class AuthorRestService implements IAuthorRest{

	@Autowired
	private INewsManagementService serviceManager;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Author addItem (Author author) throws ServiceExeption, RestException {
		
		NewsManagementVO newsVO = new NewsManagementVO(author);
		Author newAuthor = serviceManager.addAuthor(newsVO);
		return newAuthor;
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public void updateItem (Author author) throws ServiceExeption, RestException { 
		NewsManagementVO newsVO = new NewsManagementVO(author);
		serviceManager.editAuthor(newsVO);
	}
	
	@DELETE
	@Path("/{id}") 
	@Override
	public void deleteItem(@PathParam("id") Long authorId) throws ServiceExeption, RestException {
		
		serviceManager.expiredAuthor(authorId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Author> getAuthors() throws ServiceExeption, RestException {
			List<Author> authorList = serviceManager.getAuthorsList(); 
		return authorList;
	}

}
