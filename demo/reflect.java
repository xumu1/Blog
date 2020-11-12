class reflect {

    private String name;

    reflect(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        reflect r1 = new reflect("a");
        reflect r2 = new reflect("b");
        System.out.println(r1.getClass());
        System.out.println(r2.getClass() == r1.getClass());

    }
}