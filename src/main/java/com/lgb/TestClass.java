package com.lgb;

import java.math.BigDecimal;

public class TestClass {
    public String a;
    public Long b;
    private Double c;
    public BigDecimal d;

    public enum testInner{
        AA(1),BB(2);
        private int a;

        testInner(int a) {
            this.a = a;
        }
    }

    public void test(){
        new Thread(()-> System.out.println("hello"));
    }
}
