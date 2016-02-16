import java.util.List;

public interface Dao {

    Object get(int id);
    List getAll();
    void update(int id, Object object) throws DaoException;
    void remove(int id);
    void add(Object object) throws DaoException;
}
