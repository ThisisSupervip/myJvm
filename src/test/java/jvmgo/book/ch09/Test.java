package jvmgo.book.ch09;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
           new A(i);
        }
    }
}

class A {
    private int a;

    public A(int a) {
        this.a = a;
    }
}
