package com.epam.rudoi.newsportal.restful;

import com.epam.rudoi.newsportal.exeption.DAOExeption;
import com.epam.rudoi.newsportal.exeption.RestException;
import com.epam.rudoi.newsportal.exeption.ServiceExeption;

public interface ICrudRest <E>{

	/**
	 * Adds the item.
	 * This method add entity
	 * @param entity the entity
	 * @return the long
	 * @throws ServiceExeption 
	 * @throws RestException, ServiceExeption
	 */
	E addItem (E entity) throws RestException, ServiceExeption;
	
	/**
	 * Edits the item.
	 * This method edit entity
	 * @param entity the entity
	 * @return TODO
	 * @throws RestException, ServiceExeption
	 */
	void updateItem (E entity) throws RestException, ServiceExeption;
	
	/**
	 * Delete item.
	 * This method delete entity
	 * @param itemId the item id
	 * @throws RestException, ServiceExeption
	 */
	void deleteItem (Long itemId) throws RestException, ServiceExeption;
	
}
