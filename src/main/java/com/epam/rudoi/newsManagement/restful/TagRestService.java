package com.epam.rudoi.newsManagement.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/tags")
public class TagRestService {

	@Autowired
	private INewsManagementService serviceManager;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Tag addTag (Tag tag) throws ServiceExeption {
		
		System.out.println("this tag from angular " + tag);

		NewsManagementVO newsVO = new NewsManagementVO(tag);
		Tag newTag = serviceManager.addTag(newsVO);
		return newTag;
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTag (Tag tag) throws ServiceExeption { 
		
		System.out.println("this is tag fron angular " + tag);
		
		NewsManagementVO newsVO = new NewsManagementVO(tag);
		serviceManager.editTag(newsVO);
	}

	@DELETE
	@Path("/{id}") 
	public void deleteTag(@PathParam("id") Long tagId) throws ServiceExeption {
		
		System.out.println("this is param ID from angular - " + tagId);
		
		serviceManager.deleteTag(tagId);
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tag> getTag() throws ServiceExeption {
			List<Tag> tagsList = serviceManager.getTagsList(); 
		return tagsList;
	}
	
	
}
