package dao;

import dao.exceptions.DaoException;
import models.Model;

import java.util.List;

/**
 * Simple Data Access Object interface
 */
public interface Dao<T extends Model> {

    /**
     * @param id required ID
     * @return the first object which has required value
     * @throws DaoException
     */
    T get(int id) throws DaoException;

    /**
     * @return all objects from the list
     */
    List<T> getAll() throws DaoException;

    /**
     * Replaces a necessary object on the new object
     *
     * @param id required ID
     * @param newObject replacing object
     */
    void update(int id, T newObject) throws DaoException;

    /**
     * Replaces all objects
     *
     * @param newList replacing list of the objects
     */
    void updateAll(List<T> newList) throws DaoException;

    /**
     * Removes the first object which has required value
     *
     * @param id required ID
     */
    void remove(int id) throws DaoException;

    /**
     * Removes all objects
     */
    void clear() throws DaoException;

    /**
     * Adds objects to the file
     *
     * @param object added object
     */
    void add(T object) throws DaoException;

    /**
     * @return number of the objects
     */
    int size();
}
