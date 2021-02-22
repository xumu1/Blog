/**
 * Description:
 * date: 2021-2-22
 *
 * @author xumu
 */
public class testString {
    public static void main(String[] args) {
        {
            // 1. 字符串分割
            String s1 = "a.b.c";
            String s2 = "a-b-c";
            String[] split1 = s1.split("\\.");
            String[] split2 = s2.split("-");
        }
        {
            // 2. 字符串合并
            String join = String.join("-", "A", "B", "C");
            // "A-B-C"
        }
        {

        }

    }
}
