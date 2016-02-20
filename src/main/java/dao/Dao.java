package dao;

import java.util.List;

/**
 * Simple Data Access Object interface
 */
public interface Dao {

    Object get(String fieldName, Object value) throws DaoException;

    List getAll() throws DaoException;

    void update(String fieldName, Object value, Object newObject) throws DaoException;

    void updateAll(List newList) throws DaoException;

    void remove(String fieldName, Object value) throws DaoException;

    void add(Object object) throws DaoException;

    void removeAll() throws DaoException;

    int size() throws DaoException;
}
