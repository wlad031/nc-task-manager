public class XmlDaoFactory implements ResourceDaoFactory {

    @Override
    public Dao createDao(String resourceName, Class objectType) throws DaoException {
        return new XmlDao(resourceName, objectType);
    }
}
