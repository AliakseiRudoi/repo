package com.epam.rudoi.newsManagement.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.epam.rudoi.newsManagement.controller.util.SearchUtil;
import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.NewsManagementVOWrapper;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HomeRestController {

	@Autowired
	private INewsManagementService serviceManager;
	
	@GET
	@Path("/adminHome/{pageNumber}") /*"/{pageNumber}"*/
	@Produces(MediaType.APPLICATION_JSON)
	public NewsManagementVOWrapper homePage (@PathParam("pageNumber") Long pageNumber) throws ServiceExeption {
		
		SearchCriteria searchCriteria = fillSearchCriteria();
		
		Long startIndex = SearchUtil.countUpStartIndex(pageNumber);
		Long endIndex = SearchUtil.countUpEndtIndex(pageNumber);
		
		return new NewsManagementVOWrapper(serviceManager.searchNews(searchCriteria, startIndex, endIndex));
		
	}
	
	@GET
	@Path("/searchForm/{paginNum}") 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public NewsManagementVOWrapper search (@PathParam("paginNum") Long paginNum, HttpSession session, SearchCriteria searchCriteria) throws ServiceExeption {
		
		Long startIndex = SearchUtil.countUpStartIndex(paginNum);
		Long endIndex = SearchUtil.countUpEndtIndex(paginNum);
		
		if (searchCriteria.getAuthor() != null) {
			session.setAttribute("searchCriteria", searchCriteria);
		}
		
		searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");
		
		return new NewsManagementVOWrapper(serviceManager.searchNews(searchCriteria, startIndex, endIndex));
		
	}
	
	
	private SearchCriteria fillSearchCriteria() {
		Author author = new Author();
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setAuthor(author);
		return searchCriteria;
	}
	
}
