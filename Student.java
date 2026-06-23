class Parent {
    private int a;
    protected int b;
    public int c;
    int d;
    void display() {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
class Child extends Parent {
    void fn() {
        System.out.println(b);
    }
}

public class Student {
    public static void main(String[] args) {
        Parent p = new Parent();
        System.out.println(p.b);
    }
}

