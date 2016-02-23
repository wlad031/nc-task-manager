import java.util.Arrays;
import java.util.List;

public class StringSeparator {

    public static List<String> separate(String string) {
        return Arrays.asList(string.split(" "));
    }
}
