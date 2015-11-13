package com.epam.rudoi.newsManagement.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/author")
public class AuthorRestService {

	@Autowired
	private INewsManagementService serviceManager;

	@POST
	@Path("/addAuthorAction") /*"addAuthor"*/
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAuthor(NewsManagementVO newsVO) throws ServiceExeption {
	
		serviceManager.addAuthor(newsVO);

	}

	@POST
	@Path("/updateAuthorAction") /*"updateAuthor"*/
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateAuthor(NewsManagementVO newsVO) throws ServiceExeption {
		
		serviceManager.editAuthor(newsVO);

	}

	@POST
	@Path("/expireAuthorAction") /*"expireAuthor"*/
	@Consumes(MediaType.APPLICATION_JSON)
	public void expireAuthor(NewsManagementVO newsVO) throws ServiceExeption {
		serviceManager.expiredAuthor(newsVO);

	}

}
