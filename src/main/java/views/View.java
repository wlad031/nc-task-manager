package views;

import java.util.List;

public interface View<T> {
    void show(List<T> list);
}
