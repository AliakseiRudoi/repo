package com.epam.rudoi.newsManagement.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;
import com.epam.rudoi.newsportal.service.impl.jdbc.ServiceManagerImpl;

@Controller
@Path("/news/*")
public class MenuBarController {

	@Autowired
	private INewsManagementService serviceManager;
	
	@RequestMapping({"addNewsPage" })
	public ModelAndView addNewsPage() throws ServiceExeption {
		
		ModelAndView mv = new ModelAndView("addNewsPage", "newsVO", new NewsManagementVO());
				
		mvForSearch(mv);
		
		return mv;
	}
	
	
	@RequestMapping({"authorsPage" })
	public ModelAndView authorsPage() throws ServiceExeption {
		
		ModelAndView mv = new ModelAndView("authorsPage",  "newsVO", new NewsManagementVO());
		
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		
		return mv;
	}

	@RequestMapping({"tagsPage" })
	public ModelAndView tagsPage() throws ServiceExeption {
		
		ModelAndView mv = new ModelAndView("tagsPage", "newsVO", new NewsManagementVO());
				
		mv.addObject("tagsList", serviceManager.getTagsList());
		
		return mv;
	}
	
	private void mvForSearch(ModelAndView mv) throws ServiceExeption {
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		mv.addObject("tagsList", serviceManager.getTagsList());
		mv.addObject("tagsIdList", serviceManager.getTagIdList());
	}
	
}
