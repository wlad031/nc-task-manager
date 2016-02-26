package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static List<String> separate(String string) {
        return Arrays.asList(string.split(" "));
    }

    public static String toNormalCase(String string) {
        String res = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return res;
    }

    public static List<String> separate(String string, int n) {
        List<String> res = new ArrayList<>();
        String[] temp = string.split(" ");

        int i = 0;
        res.add("");

        for (String s : temp) {
            if (res.get(i).length() + s.length() + 1 >= n) {
                i += 1;
                res.add(s);
            } else {
                res.set(i, res.get(i) + s + " ");
            }
        }

        return res;
    }
}
