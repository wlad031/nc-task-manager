package dao;

import java.util.List;

/**
 * Simple Data Access Object interface
 */
public interface Dao<T> {

    <E> T get(String fieldName, E value) throws DaoException;

    List<T> getAll() throws DaoException;

    List<T> getAll(String fieldName, T value) throws DaoException;

    <E> void update(String fieldName, E value, T newObject) throws DaoException;

    void updateAll(List<T> newList) throws DaoException;

    <E> void remove(String fieldName, E value) throws DaoException;

    void clear() throws DaoException;

    void add(T object) throws DaoException;

    void add(List<T> objects) throws DaoException;

    int size();
}
