package dao;

import java.io.File;

public interface FileDaoFactory {

    Dao createDao(File file, Class objectType);
}
