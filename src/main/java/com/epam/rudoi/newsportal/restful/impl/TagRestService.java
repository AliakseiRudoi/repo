package com.epam.rudoi.newsportal.restful.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.restful.ITagRest;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/tags")
public class TagRestService implements ITagRest{

	@Autowired
	private INewsManagementService serviceManager;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Tag addItem (Tag tag) throws RestException, ServiceExeption {
		NewsManagementVO newsVO = new NewsManagementVO(tag);
		Tag newTag = serviceManager.addTag(newsVO);
		return newTag;
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public void updateItem (Tag tag) throws ServiceExeption { 
		NewsManagementVO newsVO = new NewsManagementVO(tag);
		serviceManager.editTag(newsVO);
	}

	@DELETE
	@Path("/{id}") 
	@Override
	public void deleteItem(@PathParam("id") Long tagId) throws ServiceExeption {
		serviceManager.deleteTag(tagId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Tag> getTags() throws ServiceExeption {
			List<Tag> tagsList = serviceManager.getTagsList(); 
		return tagsList;
	}
	
}
