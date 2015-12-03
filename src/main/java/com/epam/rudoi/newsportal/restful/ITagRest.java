package com.epam.rudoi.newsportal.restful;

import java.util.List;

import com.epam.rudoi.newsportal.entity.Tag;
import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;

public interface ITagRest extends ICrudRest<Tag>{

	/**
	 * Read tags.
	 * This method read tags
	 * @param itemId the item id
	 * @return the e
	 * @throws RestException, ServiceExeption
	 */
	List<Tag> getTags() throws RestException, ServiceExeption;
	
}
