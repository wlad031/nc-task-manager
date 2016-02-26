package utils;

public class Parser {
    public static <T> Integer[] parseInt(T... strings) {
        Integer[] res = new Integer[strings.length];

        for (int i = 0; i < strings.length; i++) {
            res[i] = Integer.parseInt(strings[i].toString());
        }

        return res;
    }
}
