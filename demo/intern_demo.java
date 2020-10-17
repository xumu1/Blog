public class intern_demo {
    public static void main(String[] args) {
        String s1 = new StringBuilder("计算机").append("技术").toString();
        System.out.println(s1.intern() == s1);
        String s2 = new StringBuilder("计算机").append("技术").toString();
        System.out.println(s2.intern() == s2);
    }
}