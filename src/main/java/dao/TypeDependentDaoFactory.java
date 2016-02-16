package dao;

public interface TypeDependentDaoFactory {
    void setObjectType(Class objectType);
    Class getObjectType();
}
