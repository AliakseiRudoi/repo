package com.epam.rudoi.newsManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.rudoi.newsManagement.constants.Expression.*;

import com.epam.rudoi.newsManagement.controller.util.ConfigurationManager;
import com.epam.rudoi.newsManagement.controller.util.ValidityChecker;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;
import com.epam.rudoi.newsportal.service.impl.jdbc.ServiceManagerImpl;

@Controller

public class AuthorController {

	@Autowired
	private INewsManagementService serviceManager;
	
	
	@RequestMapping({ "/addAuthorAction" })
	public ModelAndView addCommentPage(
			@ModelAttribute("newsVO") NewsManagementVO newsVO ) throws ServiceExeption {
				
		ModelAndView mv = new ModelAndView("redirect:authorsPage");
		
		if (ValidityChecker.checkValidity(newsVO.getAuthor().getAuthorName(), EXPRESSION_FOR_TAGS_AUTHORS_TITLE)) {
			serviceManager.addAuthor(newsVO);
		} else {
			mv.addObject("notValid", ConfigurationManager.getProperty("message.fail.authorName"));
		}
		
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		
		return mv;
	}
	
	@RequestMapping({ "/redirectToEditAuthorPage" })
	public ModelAndView redirectToEditAuthorPage(
			@ModelAttribute("newsVO") NewsManagementVO newsVO) throws ServiceExeption {
				
		ModelAndView mv = new ModelAndView("editAuthorsPage");
		
		mv.addObject("newsVO", newsVO);
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		
		return mv;
	}
	
	@RequestMapping({ "/updateAuthorAction" })
	public ModelAndView updateAuthorAction(
			@ModelAttribute("newsVO") NewsManagementVO newsVO) throws ServiceExeption {
				
		ModelAndView mv;

		if (ValidityChecker.checkValidity(newsVO.getAuthor().getAuthorName(), EXPRESSION_FOR_TAGS_AUTHORS_TITLE)) {
			mv = new ModelAndView("redirect:authorsPage");
			serviceManager.editAuthor(newsVO);
		} else {

			mv = new ModelAndView("editAuthorsPage");
			mv.addObject("newsVO", newsVO);
			mv.addObject("notValid",  ConfigurationManager.getProperty("message.fail.authorName"));

		}
		
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		
		return mv;
	}
	
	@RequestMapping({ "/expireAuthorAction" })
	public ModelAndView expireAuthorAction(
			@ModelAttribute("newsVO") NewsManagementVO newsVO) throws ServiceExeption {
				
		serviceManager.expiredAuthor(newsVO);
		ModelAndView mv = new ModelAndView("authorsPage");
		
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		
		return mv;
	}
	
}
