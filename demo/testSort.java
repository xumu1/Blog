import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.Locale;

/**
 * Description:
 * date: 2021-1-21
 *
 * @author xumu
 */
public class testSort {
    public static void main(String[] args) {
//        String s = "c.ba";
//        char[] chars = s.toCharArray();
//        Arrays.sort(chars);
//        System.out.println(chars);
//        String[] split = s.split(".");
//        System.out.println(split);
//        int[] a = new int[]{5,2,3};
//        System.out.println(1);
        String mac = "01-00-5e-xx-xx-xx";
        String s = mac.toUpperCase(Locale.ROOT);
        System.out.println(s);
        StringBuffer str5 = new StringBuffer();
        for (String item : s.split("-")) {
            str5.append(item);
        }
        System.out.println(str5);
    }

}
