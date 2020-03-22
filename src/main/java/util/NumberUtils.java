package util;

/**
 * @author yhp
 * @date 2020/3/22
 */
public class NumberUtils {

    public static boolean numeric(String num) {
        if (num == null || num.length() == 0) {
            return false;
        }
        return num.matches("^-?\\d+(\\.\\d+)?$");
    }

}
