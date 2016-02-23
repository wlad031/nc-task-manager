package dao;

import java.util.List;

/**
 * Simple Data Access Object interface
 */
public interface Dao<T> {

    /**
     * @param fieldName name of the field for search
     * @param value     required value
     * @return the first object which has required value
     * @throws DaoException
     */
    <E> T get(String fieldName, E value) throws DaoException;

    /**
     * @return all objects from the list
     */
    List<T> getAll() throws DaoException;

    /**
     * Replaces a necessary object on the new object
     *
     * @param fieldName name of the field for search
     * @param value     required value
     * @param newObject replacing object
     */
    <E> void update(String fieldName, E value, T newObject) throws DaoException;

    /**
     * Replaces all objects
     *
     * @param newList replacing list of the objects
     */
    void updateAll(List<T> newList) throws DaoException;

    /**
     * Removes the first object which has required value
     *
     * @param fieldName name of the field for search
     * @param value     required value
     * @param <E>       type of the required value
     */
    <E> void remove(String fieldName, E value) throws DaoException;

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
