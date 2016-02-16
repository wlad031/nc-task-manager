import java.io.File;

public interface ResourceDaoFactory {

    Dao createDao(String resourceName, Class objectType) throws DaoException;
}
