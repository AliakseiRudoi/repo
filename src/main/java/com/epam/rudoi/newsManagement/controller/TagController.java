package com.epam.rudoi.newsManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.rudoi.newsManagement.constants.Expression.*;

import javax.ws.rs.Path;

import com.epam.rudoi.newsManagement.controller.util.ConfigurationManager;
import com.epam.rudoi.newsManagement.controller.util.ValidityChecker;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;
import com.epam.rudoi.newsportal.service.impl.jdbc.ServiceManagerImpl;

@Controller
public class TagController {

	@Autowired
	private INewsManagementService serviceManager;

	@RequestMapping({ "/addTagAction" })
	public ModelAndView addTagPage(
			@ModelAttribute("newsVO") NewsManagementVO newsVO)
			throws ServiceExeption {

		ModelAndView mv = new ModelAndView("redirect:tagsPage");

		if (ValidityChecker.checkValidity(newsVO.getTag().getTagName(), EXPRESSION_FOR_TAGS_AUTHORS_TITLE)) {
			serviceManager.addTag(newsVO);
		} else {
			mv.addObject("notValid", ConfigurationManager.getProperty("message.fail.tagName"));
		}

		mv.addObject("tagsList", serviceManager.getTagsList());

		return mv;
	}

	@RequestMapping({ "/redirectToEditTagPage" })
	public ModelAndView redirectToEditTagPage(
			@ModelAttribute("newsVO") NewsManagementVO newsVO)
			throws ServiceExeption {

		ModelAndView mv = new ModelAndView("editTagsPage");

		mv.addObject("newsVO", newsVO);
		mv.addObject("tagsList", serviceManager.getTagsList());

		return mv;
	}

	@RequestMapping({ "/updateTagAction" })
	public ModelAndView updateTagAction(
			@ModelAttribute("newsVO") NewsManagementVO newsVO)
			throws ServiceExeption {

		ModelAndView mv;

		if (ValidityChecker.checkValidity(newsVO.getTag().getTagName(), EXPRESSION_FOR_TAGS_AUTHORS_TITLE)) {
			mv = new ModelAndView("redirect:tagsPage");
			serviceManager.editTag(newsVO);
		} else {

			mv = new ModelAndView("editTagsPage");
			mv.addObject("newsVO", newsVO);
			mv.addObject("notValid", ConfigurationManager.getProperty("message.fail.tagName"));

		}

		mv.addObject("tagsList", serviceManager.getTagsList());

		return mv;
	}

	@RequestMapping({ "/deleteTagAction" })
	public ModelAndView deleteTagAction(
			@ModelAttribute("newsVO") NewsManagementVO newsVO)
			throws ServiceExeption {

		serviceManager.deleteTag(newsVO.getTag().getTagId());
		ModelAndView mv = new ModelAndView("tagsPage");

		mv.addObject("tagsList", serviceManager.getTagsList());

		return mv;
	}

}
