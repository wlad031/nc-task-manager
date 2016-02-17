package dao;

import java.util.List;

public interface Dao {

    Object get(int id) throws DaoException;

    List getAll() throws DaoException;

    void update(int id, Object object) throws DaoException;

    void remove(int id) throws DaoException;

    void add(Object object) throws DaoException;
}
