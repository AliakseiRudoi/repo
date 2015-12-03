package com.epam.rudoi.newsportal.restful;

import java.util.List;

import javax.ws.rs.PathParam;

import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.entity.Comment;
import com.epam.rudoi.newsportal.entity.News;
import com.epam.rudoi.newsportal.entity.NewsManagementVO;
import com.epam.rudoi.newsportal.entity.SearchCriteria;
import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;

public interface INewsRest {
	
	/**
	 * Adds the News.
	 * This method add News
	 * @param NewsManagementVO the NewsManagementVO
	 * @return the News
	 * @throws ServiceExeption 
	 * @throws RestException, ServiceExeption
	 */
	News addNews (NewsManagementVO newsManagementVO) throws RestException, ServiceExeption;
	
	/**
	 * Edits the News.
	 * This method edit News
	 * @param NewsManagementVO the NewsManagementVO
	 * @return Boolean
	 * @throws RestException, ServiceExeption
	 */
	Boolean updateNews (NewsManagementVO newsManagementVO) throws RestException, ServiceExeption;
	
	/**
	 * Delete news.
	 * This method delete News
	 * @param NewsManagementVO the NewsManagementVO
	 * @return void
	 * @throws RestException, ServiceExeption
	 */
	void deleteNews (NewsManagementVO newsManagementVO) throws RestException, ServiceExeption;
	
	
	/**
	 * Get list news.
	 * This method get news list
	 * @param itemId the item id
	 * @return the List<NewsManagementVO>
	 * @throws RestException, ServiceExeption
	 */
	List<NewsManagementVO> getNews() throws RestException, ServiceExeption;
	
	/**
	 * Read news.
	 * This method read news
	 * @param itemId the item id
	 * @return the NewsManagementVO
	 * @throws RestException, ServiceExeption
	 */
	NewsManagementVO getSingleNews(Long newsId) throws RestException, ServiceExeption;
	
	/**
	 * Get list authors.
	 * This method get authors list 
	 * @param entity the entity
	 * @return TODO
	 * @throws RestException, ServiceExeption
	 */
	List<Author> getAuthors() throws RestException, ServiceExeption;
	
	/**
	 * Read tags.
	 * This method read tags
	 * @param itemId the item id
	 * @return the e
	 * @throws RestException, ServiceExeption
	 */
	List<Tag> getTags() throws RestException, ServiceExeption;
	
	/**
	 * Search news.
	 * This method search news by params
	 * @param searchCriteria the search criteria
	 * @return the list
	 * @throws RestException, ServiceExeption
	 */
	List<NewsManagementVO> searchNews(SearchCriteria searchCriteria) throws RestException, ServiceExeption;
	
	/**
	 * Count all find news.
	 * This method count all news that find
	 * @return the long 
	 * @throws RestException, ServiceExeption
	 */
	SearchCriteria getCountNews(SearchCriteria searchCriteria) throws RestException, ServiceExeption;
	
	/**
	 * Makes pagination on news pages.
	 * This method paginate news pages
	 * @return the long 
	 * @throws RestException, ServiceExeption
	 */
	List<NewsManagementVO> ajaxSearchNews(SearchCriteria searchCriteria) throws RestException, ServiceExeption;
	
	/**
	 * Get comments list.
	 * This method get comments list
	 * @param itemId the item id
	 * @return the e
	 * @throws RestException, ServiceExeption
	 */
	List<Comment> getComments(Long newsId) throws RestException, ServiceExeption;
	
	/**
	 * Delete comment.
	 * This method delete comment
	 * @param itemId the item id
	 * @throws RestException, ServiceExeption
	 */
	void deleteComment(Long commentId) throws RestException, ServiceExeption;
	
	/**
	 * Adds the comment.
	 * This method add comment
	 * @param entity the entity
	 * @return the long
	 * @throws ServiceExeption 
	 * @throws RestException, ServiceExeption
	 */
	Comment addComment(Comment comment) throws RestException, ServiceExeption;
}
