package jvmgo.book.ch07;

public class InvokeDemo implements Runnable {
    public static void main(String[] args) {
        InvokeDemo demo = new InvokeDemo();
        demo.test();
    }


    public void test() {
        InvokeDemo.staticMethod();              //invoke_static
        InvokeDemo demo = new InvokeDemo();     //invoke_spacial
        demo.instanceMethod();                  //invoke_spacial
        super.equals(null);                     //invoke_spacial
        this.run();                             //invoke_virtual
        ((Runnable) demo).run();                //invoke_interface
    }

    public static void staticMethod() {

    }

    private void instanceMethod() {

    }


    @Override
    public void run() {

    }
}
