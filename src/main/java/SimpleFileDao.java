import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SimpleFileDao extends Dao {

    public SimpleFileDao(Class modelClass, File file) {
        super(modelClass, file);
    }

    @Override
    public Object getModel(int id) {
        return null;
    }

    @Override
    public List<Object> getAllModels() {
        return null;
    }

    @Override
    public void updateModel(int id, Object model) {

    }

    @Override
    public void addModel(Object model) {
        if (model.getClass() != this.modelClass) {
            throw new IllegalArgumentException("Wrong object type");
        }

        try (FileWriter fw = new FileWriter(file, true)) {

            Method[] modelGetters = model.getClass().getMethods();

            StringBuilder sb = new StringBuilder();

            for (Method method : modelGetters) {
                if (method.isAnnotationPresent(Getter.class)) {

                    try {
                        Annotation getterAnnotation = method.getAnnotation(Getter.class);
                        String fieldName = (String) getterAnnotation.annotationType()
                                .getDeclaredMethod("fieldName").invoke(getterAnnotation);
                        Field field = modelClass.getDeclaredField(fieldName);
                        field.setAccessible(true);

                        sb.append(fieldName + ": " + field.get(model) + "\n");

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }

                }
            }

            fw.write(sb.toString() + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeModel(int id) {

    }
}
