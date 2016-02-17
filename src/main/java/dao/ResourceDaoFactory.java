package dao;

public interface ResourceDaoFactory {

    Dao createDao(String resourceName, Class objectType) throws DaoException;
}
