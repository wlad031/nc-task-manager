import java.util.Arrays;
import java.util.List;

public class StringSeparator {

    public static List<Object> separate(String string) {
        return Arrays.asList(string.split(" "));
    }
}
