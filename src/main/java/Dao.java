import java.io.File;
import java.util.List;

public abstract class Dao {

    protected Class modelClass;
    protected File file;

    public Dao(Class modelClass, File file) {
        this.modelClass = modelClass;
        this.file = file;
    }

    public abstract Object getModel(int id);
    public abstract List<Object> getAllModels();
    public abstract void updateModel(int id, Object model);
    public abstract void addModel(Object model);
    public abstract void removeModel(int id);
}
