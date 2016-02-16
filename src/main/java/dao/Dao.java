package dao;

public interface Dao {

    Object get(int id);
    Object[] getAll();
    void update(int id, Object object);
    void create(Object object);
    void remove(int id);
}
