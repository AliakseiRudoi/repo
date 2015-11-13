package com.epam.rudoi.newsManagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epam.rudoi.newsManagement.controller.util.ConfigurationManager;
import com.epam.rudoi.newsManagement.controller.util.SearchUtil;
import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

@Controller
@Path("/news")
public class AdminHomeController {

	@Autowired
	private INewsManagementService serviceManager;

	private static final int INDEX_PAGINATION = 1;
	private static final int NEWS_ON_PAGE = 10;

	@RequestMapping(value = "/adminHome/pagin/{numberPage}", method = RequestMethod.GET)
	public @ResponseBody List<NewsManagementVO> getNewsVOListByAjaxPagination(@PathVariable Long numberPage,HttpSession session) throws ServiceExeption {
		
		Long startIndex = SearchUtil.countUpStartIndex(numberPage);
		Long endIndex = SearchUtil.countUpEndtIndex(numberPage);
		
		SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");

		List<NewsManagementVO> newsVO = serviceManager.searchNews(searchCriteria, startIndex, endIndex);
		
		return newsVO;
	}

	@RequestMapping({ "/" })
	public ModelAndView loginPage() throws ServiceExeption, OAuthSystemException {
		return new ModelAndView("loginPage");
	}

	@RequestMapping({ "/logout" }) 
	public ModelAndView logoutPage() throws ServiceExeption {
		return new ModelAndView("loginPage");
	}

	@RequestMapping({ "/fail2login" })
	public ModelAndView failLoginPage() throws ServiceExeption {

		ModelAndView mv = new ModelAndView("loginPage");

		mv.addObject("failMessage", ConfigurationManager.getProperty("message.fail.loginOrPassword"));

		return mv;
	}

	@RequestMapping({ "/reset" })
	public ModelAndView resetSearch() throws ServiceExeption {

		ModelAndView mv = new ModelAndView("redirect:/adminHome");

		return mv;
	}

	@RequestMapping({ "/adminHome/{pageNumber}" })
	public ModelAndView showAdminHomePage(@PathVariable Long pageNumber, HttpSession session) throws ServiceExeption {

		ModelAndView mv = new ModelAndView("adminHome", "searchCriteria", new SearchCriteria());
		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());

		Long startIndex = SearchUtil.countUpStartIndex(pageNumber);
		Long endIndex = SearchUtil.countUpEndtIndex(pageNumber);

		SearchCriteria searchCriteria = fillSearchCriteria();
		searchCriteria.setPageNumber(1L);
		session.setAttribute("searchCriteria", searchCriteria);

		List<NewsManagementVO> newsVOList = serviceManager.searchNews(searchCriteria, startIndex, endIndex);

		System.out.println("newsVOList" + newsVOList);
		
		mv.addObject("pageNumber", pageNumber);
		mv.addObject("paginNum", getPuginNumber(searchCriteria));
		mv.addObject("firtPage", 1);
		mv.addObject("lastPage", getPuginNumber(searchCriteria));

		mv.addObject("newsVO", newsVOList);

		mvForSearch(mv);

		return mv;
	}

	@RequestMapping({ "/searchForm/{paginNum}" })
	public ModelAndView searchCriteriaCheaker(@PathVariable Long paginNum, HttpSession session,
			@ModelAttribute("searchCriteria") SearchCriteria searchCriteria) throws ServiceExeption {
		
		Long startIndex = SearchUtil.countUpStartIndex(paginNum);
		Long endIndex = SearchUtil.countUpEndtIndex(paginNum);

		if (searchCriteria.getAuthor() != null) {
			searchCriteria.setPageNumber(1L);
			session.setAttribute("searchCriteria", searchCriteria);
		}

		return searchNews(session, startIndex, endIndex, paginNum);
	}

	public ModelAndView searchNews(HttpSession session, Long startIndex, Long endIndex, Long paginNum)
			throws ServiceExeption {

		SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");
		
		List<NewsManagementVO> newsVOList = new ArrayList<NewsManagementVO>();
		newsVOList = serviceManager.searchNews(searchCriteria, startIndex, endIndex);

		ModelAndView mv = new ModelAndView("searchPage", "searchCriteria", new SearchCriteria());
		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());
		
		mv.addObject("pageNumber", paginNum);
		mv.addObject("paginNum", getPuginNumber(searchCriteria));
		mv.addObject("searchCriteriaResult", searchCriteria);
		mv.addObject("firtPage", 1);
		mv.addObject("lastPage", getPuginNumber(searchCriteria));

		mv.addObject("newsVO", newsVOList);
		mvForSearch(mv);

		return mv;
	}

	private void mvForSearch(ModelAndView mv) throws ServiceExeption {
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		mv.addObject("tagsList", serviceManager.getTagsList());
		mv.addObject("tagsIdList", serviceManager.getTagIdList());
	}

	private Long getPuginNumber(SearchCriteria searchCriteria) throws ServiceExeption {
		Long newsCount = serviceManager.countSearchNews(searchCriteria);
		Long pnum = (newsCount - INDEX_PAGINATION) / NEWS_ON_PAGE + INDEX_PAGINATION;
		return pnum;
	}

	private SearchCriteria fillSearchCriteria() {
		Author author = new Author();
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setAuthor(author);
		return searchCriteria;
	}

}
