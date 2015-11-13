package com.epam.rudoi.newsManagement.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.rudoi.newsManagement.controller.util.ConfigurationManager;
import com.epam.rudoi.newsManagement.controller.util.SearchUtil;
import com.epam.rudoi.newsManagement.controller.util.ValidityChecker;
import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.Comment;
import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;
import com.epam.rudoi.newsportal.service.INewsManagementService;

import static com.epam.rudoi.newsManagement.constants.Expression.*;

@Controller
public class NewsController {

	@Autowired
	private INewsManagementService serviceManager;

	@RequestMapping({ "/addCommentAdmin" })
	public ModelAndView addCommentPage(@ModelAttribute("comment") Comment comment) throws ServiceExeption {
System.out.println(comment);
		if (ValidityChecker.checkValidity(comment.getCommentText(), EXPRESSION_FOR_SHORTTEXT_COMMENTS)) {
			comment.setCreationDate(new Date());
			serviceManager.addComment(comment);
		} else {
			ModelAndView mv = new ModelAndView("redirect:/editSingleNews");
			mv.addObject("messageCommentNotValid", ConfigurationManager.getProperty("message.fail.maxLengthComment"));
			return mv;
		}

		ModelAndView mv = new ModelAndView("redirect:/editSingleNews?newsId=" + comment.getNewsId());

		return mv;
	}

	@RequestMapping({ "/updateNews" })
	public ModelAndView updateNewsAction(@ModelAttribute("newsVO1") NewsManagementVO newsVO) throws ServiceExeption {

		  newsVO.getNews().setNewsCreationDate(new Date());
		  newsVO.getNews().setNewsModificationDate(new Date());
		 
		Author author = newsVO.getAuthor();
		List<Author> authorsList = new ArrayList<Author>();
		authorsList.add(author);
		List<Tag> tagsList = newsVO.getTagsList();
		
		newsVO.getNews().setAuthorsList(authorsList);
		newsVO.getNews().setTagsList(tagsList);

		boolean allowToUpdateCurrentNews = serviceManager.editNews(newsVO);
System.out.println(allowToUpdateCurrentNews);
		ModelAndView mv = new ModelAndView("editSingleNews", "comment", new Comment());
		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());
		if (!allowToUpdateCurrentNews) {
			mv.addObject("failMessage", ConfigurationManager.getProperty("message.fail.updateNews"));
		}
		mv.addObject("newsVO", serviceManager.showSinglNews(newsVO.getNews().getNewsId()));
		mv.addObject("commentsList", serviceManager.getCommentsList(newsVO.getNews().getNewsId()));
		mv.addObject("paginNum", serviceManager.countAllNews());
		mv.addObject("queryString", "&newsId=" + newsVO.getNews().getNewsId());
		mv.addObject("currentNewsId", newsVO.getNews().getNewsId());
		mvForSearch(mv);

		return mv;

	}

	@RequestMapping({ "/deleteCommentAdmin" })
	public ModelAndView deleteComment(@ModelAttribute("comment") Comment comment) throws ServiceExeption {

		serviceManager.deleteComments(comment.getCommentId());

		ModelAndView mv = new ModelAndView("redirect:/editSingleNews?newsId=" + comment.getNewsId());

		return mv;
	}
	
	@RequestMapping({ "addNewsAction/{paginNum}" })
	public ModelAndView addNewsAction(@PathVariable Long paginNum, HttpSession session,
			@ModelAttribute("newsVO") NewsManagementVO newsVO) throws ServiceExeption {

		if (newsVO.getAuthor() == null | newsVO.getTagsIdList() == null) {
			ModelAndView mv = new ModelAndView("addNewsPage", "newsVO", new NewsManagementVO());
			mv.addObject("messageMissAuthorOrTags", ConfigurationManager.getProperty("message.fail.newsAdd"));
			mvForSearch(mv);
			return mv;
		} else {

			if (ValidityChecker.checkValidity(newsVO.getNews().getNewsTitle(), EXPRESSION_FOR_TAGS_AUTHORS_TITLE)) {

				if (ValidityChecker.checkValidity(newsVO.getNews().getNewsShortText(),
						EXPRESSION_FOR_SHORTTEXT_COMMENTS)) {

					if (ValidityChecker.checkValidity(newsVO.getNews().getNewsFullText(), EXPRESSION_FOR_FULLTEXT)) {

					} else {
						ModelAndView mv = new ModelAndView("addNewsPage", "newsVO", new NewsManagementVO());
						mv.addObject("messageFullTextNotValid", "length size must be 3 - 2000 symbols");
						mvForSearch(mv);
						return mv;
					}

				} else {
					ModelAndView mv = new ModelAndView("addNewsPage", "newsVO", new NewsManagementVO());
					mv.addObject("messageShortTextNotValid", "length size must be 3 - 100 symbols");
					mvForSearch(mv);
					return mv;
				}

			} else {
				ModelAndView mv = new ModelAndView("addNewsPage", "newsVO", new NewsManagementVO());
				mv.addObject("messageTitleNotValid", "length size must be 3 - 30 symbols");
				mvForSearch(mv);
				return mv;
			}
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

			serviceManager.addNews(newsVO);
		}

		Long startIndex = SearchUtil.countUpStartIndex(paginNum);
		Long endIndex = SearchUtil.countUpEndtIndex(paginNum);

		SearchCriteria searchCriteria = fillSearchCriteria(session);
		List<NewsManagementVO> newsVOList = serviceManager.searchNews(searchCriteria, startIndex, endIndex);

		ModelAndView mv = new ModelAndView("redirect:/adminHome/1", "searchCriteria", new SearchCriteria());

		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());

	//	newsVOList = newsVOList.subList(0, 3);
		mv.addObject("newsVO", newsVOList);
		mvForSearch(mv);

		return mv;
	}

	@RequestMapping({ "/editSingleNews" })
	public ModelAndView showSingleNewsPage(HttpSession session, @RequestParam("newsId") Long newsId)
			throws ServiceExeption {
		SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");
		Long countNews = serviceManager.countSearchNews(searchCriteria);
		Long startIndex = SearchUtil.countUpStartIndex(1L);
		Long endIndex = SearchUtil.countUpEndtIndex(countNews);
		List<NewsManagementVO> newsVO = serviceManager.searchNews(searchCriteria, startIndex, endIndex);
		Long resultNextNewsId = null;
		Long resultPreviousNewsId = null;

		for (int i = 0; i < newsVO.size(); i++) {
			if (newsVO.get(i).getNews().getNewsId().equals(newsId)) {

				if (newsVO.listIterator(i).hasPrevious()) {
					NewsManagementVO newsManagementVOprev = newsVO.listIterator(i).previous();
					resultPreviousNewsId = (long) newsManagementVOprev.getNews().getNewsId();
				}
				if (newsVO.listIterator(i).hasNext()) {
					int nextIndex = ++i;
					resultNextNewsId = newsVO.get(nextIndex).getNews().getNewsId();
				}

				break;
			}

		}

		ModelAndView mv = new ModelAndView("editSingleNews", "comment", new Comment());
		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());
		mv.addObject("resultNextNewsId", resultNextNewsId);
		mv.addObject("resultPreviousNewsId", resultPreviousNewsId);
		mv.addObject("newsVO", serviceManager.showSinglNews(newsId));
		mv.addObject("commentsList", serviceManager.getCommentsList(newsId));
		mv.addObject("paginNum", serviceManager.countAllNews());
		mv.addObject("queryString", "&newsId=" + newsId);
		mv.addObject("currentNewsId", newsId);

		mvForSearch(mv);

		return mv;
	}

	@RequestMapping({ "/delNews" })
	public ModelAndView deleteNews(HttpSession session,
			@ModelAttribute("newsVO1") NewsManagementVO newsVO1) throws ServiceExeption {
		System.out.println(newsVO1);
		Long paginNum = 1L;
		Long startIndex = SearchUtil.countUpStartIndex(paginNum);
		Long endIndex = SearchUtil.countUpEndtIndex(paginNum);

		serviceManager.deleteNews(newsVO1);
		ModelAndView mv = new ModelAndView("redirect:/adminHome/1", "searchCriteria", new SearchCriteria());
		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());

		SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");

		List<NewsManagementVO> newsVOList = serviceManager.searchNews(searchCriteria, startIndex, endIndex);

		mv.addObject("newsVO", newsVOList);
		
		
		mvForSearch(mv);

		return mv;
	}
	
	@RequestMapping({ "/delNews/{newsId}" })
	public ModelAndView deleteNewsAjax(HttpSession session, @PathVariable Long newsId) throws ServiceExeption {
		Long paginNum = 1L;
		Long startIndex = SearchUtil.countUpStartIndex(paginNum);
		Long endIndex = SearchUtil.countUpEndtIndex(paginNum);

		NewsManagementVO newsVO1 = new NewsManagementVO();
		News news = new News(newsId);
		newsVO1.setNews(news);
		
		serviceManager.deleteNews(newsVO1);
		ModelAndView mv = new ModelAndView("redirect:/adminHome/1", "searchCriteria", new SearchCriteria());
		mv.getModelMap().addAttribute("newsVO1", new NewsManagementVO());

		SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");

		List<NewsManagementVO> newsVOList = serviceManager.searchNews(searchCriteria, startIndex, endIndex);

		mv.addObject("newsVO", newsVOList);
		
		
		mvForSearch(mv);

		return mv;
	}

	private SearchCriteria fillSearchCriteria(HttpSession session) {
		SearchCriteria searchCriteria = (SearchCriteria) session.getAttribute("searchCriteria");
		return searchCriteria;
	}

	private void mvForSearch(ModelAndView mv) throws ServiceExeption {
		mv.addObject("authorsList", serviceManager.getAuthorsList());
		mv.addObject("tagsList", serviceManager.getTagsList());
		mv.addObject("tagsIdList", serviceManager.getTagIdList());
	}

}
