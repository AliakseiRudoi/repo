package com.epam.rudoi.newsportal.restful;

import java.util.List;

import com.epam.rudoi.newsportal.entity.Author;
import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;

public interface IAuthorRest extends ICrudRest<Author>{
	
	/**
	 * Get list authors.
	 * This method get authors list 
	 * @param entity the entity
	 * @return TODO
	 * @throws RestException, ServiceExeption
	 */
	List<Author> getAuthors() throws RestException, ServiceExeption;

}
