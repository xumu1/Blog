import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class intern_demo {
    public static void main(String[] args) {
//        String s1 = new StringBuilder("计算机").append("技术").toString();
//        System.out.println(s1.intern() == s1);
//        String s2 = new StringBuilder("计算机").append("技术").toString();
//        System.out.println(s2.intern() == s2);


//        boolean[] booleans = new boolean[5];
//        System.out.println(1);
        StringBuilder sb = new StringBuilder("a.b..c");
        String s = "a.b..c";
        String[] split = s.split("\\.");
        List<String> collect = Arrays.stream(split).filter((s1 -> !"".equals(s1))).collect(Collectors.toList());
        System.out.println(1);

    }
}