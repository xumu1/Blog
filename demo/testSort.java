import javax.lang.model.element.VariableElement;
import java.util.Arrays;

/**
 * Description:
 * date: 2021-1-21
 *
 * @author xumu
 */
public class testSort {
    public static void main(String[] args) {
        String s = "c.ba";
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        System.out.println(chars);
        String[] split = s.split(".");
        System.out.println(split);
        int[] a = new int[]{5,2,3};
        System.out.println(1);

    }
}
